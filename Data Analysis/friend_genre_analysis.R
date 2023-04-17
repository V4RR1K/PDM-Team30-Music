# Greg Lynskey

rm(list= ls())
library(ggplot2)

# - Friend correlation to Music Genre
#   - Hypothesis: Do less followers lead to listening to sad music
friend_genre <- read.csv("friend_genre.csv", header=TRUE)