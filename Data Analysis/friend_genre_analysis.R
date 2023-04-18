# Greg Lynskey

rm(list= ls())
library(ggplot2)

# - Friend correlation to Music Genre
#   - Hypothesis: Do less followers lead to listening to sad music
#   - DataNeeded: Count of follow occurances (get min) vs genre
friend_genre <- read.csv("friend_genre.csv", header=TRUE)

follow_counter <- table(friend_genre$follows)
fc_df <- as.data.frame(follow_counter)

colnames(fc_df) <- c("User", "NumFriends")
fc_df_min <- fc_df[order(fc_df$NumFriends),]
fc_df_max <- fc_df[order(-fc_df$NumFriends),]

