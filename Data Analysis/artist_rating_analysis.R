# Greg Lynskey

rm(list= ls())
library(ggplot2)

# - Highest paid artists (pib2000 pays artists $0.02 per listen)
#   - Hypothesis: Are higher paid artists rated higher?
#   - DataNeeded: Get top 5 rated artists vs bottom 5 rated artists show play count
# TODO: Need another query of listened data (LISTEN -> SONG -> PRODUCES -> ARTIST)
artist_rating <- read.csv("artist_rating.csv", header=TRUE)