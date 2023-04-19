# Greg Lynskey

rm(list= ls())
library(ggplot2)
library(data.table)

# - Highest paid artists (pib2000 pays artists $0.02 per listen)
#   - Hypothesis: Are higher paid artists rated higher?
#   - DataNeeded: Get top 5 rated artists vs bottom 5 rated artists show play count

artist_rating <- read.csv("artist_rating.csv", header=TRUE)
artist_listens <- read.csv("artist_listens.csv", header=TRUE)


# Get Artist Listen Frequency
artist_counter_t <- table(artist_listens$Name)
artist_counter_df <- as.data.frame(artist_counter_t)
colnames(artist_counter_df) <- c("artist", "frequency")
artist_counter_df <- artist_counter_df[order(-artist_counter_df$frequency),]

# Get Artist Rating
artist_rating_df <- data.frame(
  artist_rating$Name,
  artist_rating$rating
)
colnames(artist_rating_df) <- c("artist", "rating")

artist_rating_aggregate <- aggregate(rating ~ artist, artist_rating_df, mean)

plot_data <- merge(artist_counter_df, artist_rating_aggregate)

# Scatter Plot ( x = listen Frequency, y = Average Rating )
p <- ggplot(plot_data, aes(x=plot_data$frequency, y=plot_data$rating)) +
  geom_point()

p + labs(title="Listens vs Rating Correlation") +
  xlab("Frequency") + ylab("Rating") + xlim(0,40) +ylim(0,5) + theme_minimal(base_size = 16)
