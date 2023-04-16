# Greg Lynskey

rm(list= ls())
library(ggplot2)


# - Most Listened to Genre By Year
#   - Hypothesis: More sad music was listened to during covid
most_listened <- read.csv("mostlistened_year.csv", header=TRUE)
# - Friend correlation to Music Genre
#   - Hypothesis: Do less followers lead to listening to sad music

# - Highest paid artists (pib2000 pays artists $0.02 per listen)
#   - Hypothesis: Are higher paid artists rated higher?