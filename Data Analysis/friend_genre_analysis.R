# Greg Lynskey

rm(list= ls())
library(ggplot2)

# - Friend correlation to Music Genre
#   - Hypothesis: Do less followers lead to listening to sad music
#   - DataNeeded: Count of follow occurances (get min) vs genre
friend_genre <- read.csv("friend_genre.csv", header=TRUE)

fri_gen_df <- data.frame(
  friend_genre$follows,
  friend_genre$genrename
)
colnames(fri_gen_df) <- c("user", "genre")

follow_counter <- table(friend_genre$follows)
fc_df <- as.data.frame(follow_counter)

colnames(fc_df) <- c("User", "NumFriends")
fc_df_min <- fc_df[order(fc_df$NumFriends),]
fc_df_max <- fc_df[order(-fc_df$NumFriends),]

# Get users most listened to genres
user_top_genre <- function(u_id){
  top <- fri_gen_df[fri_gen_df$user == u_id,]
  top_counter_t <- table(top$genre)
  top_coutner_df <- as.data.frame(top_counter_t)
  colnames(top_coutner_df) <- c("genere", "plays")
  top_coutner_df <- top_coutner_df[order(-top_coutner_df$plays),]
  return(top_coutner_df[1:5,])
}

test <- user_top_genre(2)