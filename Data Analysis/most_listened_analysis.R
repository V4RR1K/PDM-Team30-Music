# Greg Lynskey

rm(list= ls())

library(ggplot2)
library(lubridate)

# - Most Listened to Genre By Year
#   - Hypothesis: More sad music was listened to during covid
#   - DataNeeded: Top 5 Genres per Year
most_listened_raw <- read.csv("mostlistened_year.csv", header=TRUE)

# Clean up year data
date_raw <- ymd_hms(most_listened_raw$listened_to_datetime)
years <- format(date_raw, format="%Y")
head(years)


most_listened <- data.frame(
  years,
  most_listened_raw$genrename
)
colnames(most_listened) <- c("year", "genre")

# Parse out Target Year
target_year <- 2022

color <- "blue"

if (target_year == 2020){
  color <- "#a44b5e"
} else if (target_year == 2021){
  color <- "#27435c"
} else if (target_year == 2022){
  color <- "#19554b"
}

most_listened_year <- most_listened[most_listened$year == target_year,]

# Get frequency
genre_count_tbl <- table(most_listened_year$genre)
head(genre_count_tbl)

# Convert to DataFrame and sort
genre_count_df <- as.data.frame(genre_count_tbl)
colnames(genre_count_df) <- c("genre", "frequency")
genre_count_df <- genre_count_df[order(-genre_count_df$frequency),]

# Get Top
gc_top <- head(genre_count_df)

p <- ggplot(gc_top, aes(x=gc_top$genre, y=gc_top$frequency)) +
        geom_bar(stat="identity", fill=color)
titlestr <- paste("Most Popular Genres in" , target_year, sep=" ")

p + labs(title=titlestr) + xlab("Genre") + ylab("Number of Listens") + ylim(0,15) +theme_minimal()

