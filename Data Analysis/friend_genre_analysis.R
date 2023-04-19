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
rownames(fc_df_min) <- seq_len(nrow(fc_df_min))
fc_df_max <- fc_df[order(-fc_df$NumFriends),]
rownames(fc_df_max) <- seq_len(nrow(fc_df_max))

# Get users most listened to genres
user_top_genre <- function(u_id){
  top <- fri_gen_df[fri_gen_df$user == u_id,]
  top_counter_t <- table(top$genre)
  top_counter_df <- as.data.frame(top_counter_t)
  colnames(top_counter_df) <- c("genre", "plays")
  top_counter_df <- top_counter_df[order(-top_counter_df$plays),]
  return(top_counter_df[1:5,])
}

min1 <- user_top_genre(fc_df_min$User[1])
min2 <- user_top_genre(fc_df_min$User[2])
min3 <- user_top_genre(fc_df_min$User[3])
min4 <- user_top_genre(fc_df_min$User[4])
min5 <- user_top_genre(fc_df_min$User[5])

max1 <- user_top_genre(fc_df_max$User[1])
max2 <- user_top_genre(fc_df_max$User[2])
max3 <- user_top_genre(fc_df_max$User[3])
max4 <- user_top_genre(fc_df_max$User[4])
max5 <- user_top_genre(fc_df_max$User[5])

master_min <- rbind(min1, min2, min3, min4, min5)
master_max <- rbind(max1, max2, max3, max4, max5)

p_min <- ggplot(master_min, aes(x=master_min$genre, y=master_min$plays)) +
  geom_bar(stat="identity", fill="blue")

p_min + labs(title="Low Follower Preferred Genre") + xlab("Genre") + ylab("Number of Listens") + theme_minimal()

p_max <- ggplot(master_max, aes(x=master_max$genre, y=master_max$plays)) +
  geom_bar(stat="identity", fill="red")

p_max + labs(title="High Follower Preferred Genre") + xlab("Genre") + ylab("Number of Listens") + theme_minimal()
