# Greg Lynskey

rm(list= ls())
library(ggplot2)

# - Highest paid artists (pib2000 pays artists $0.02 per listen)
#   - Hypothesis: Are higher paid artists rated higher?
artist_rating <- read.csv("artist_rating.csv", header=TRUE)