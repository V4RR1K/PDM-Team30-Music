o# Data Analytics Planning

## What trends are we looking for?

- Most Listened to Genre By Year
  - Hypothesis: More sad music was listened to during covid

- Friend correlation to Music Genre
  - Hypothesis: Do less followers lead to listening to sad music

- Highest paid artists (pib2000 pays artists $0.02 per listen)
  - Hypothesis: Are higher paid artists rated higher?

## Data Needed

- Genre Popularity by Year
  - Genre Joined with Song Joined with Listened To
- Most Listened to Songs By Year
  - Song joined with listened to
- Friend Correlation to Genre
  - Followers joined with user joined with listened to joined with song joined to genre
- Highest Paid Artist
  - Listened to joined with song joined with artist joined with rating

## Sql Queries

### Most Listened to Genre By Year
```postgresql
select "Listened_to".listened_to_datetime,
"Listened_to".s_id,
Gs.g_id,
G.genrename
from "Listened_to"
INNER JOIN "Genre_s" Gs on "Listened_to".s_id = Gs.s_id
INNER JOIN "Genre" G on G.g_id = Gs.g_id
```

### Friend Count to Genre
```postgresql
select "Follows".follows,
       Lt.listened_to_datetime,
       Lt.s_id,
       Gs.g_id,
       G.genrename
FROM "Follows"
INNER JOIN "Listened_to" Lt on "Follows".follows = Lt.u_id
INNER JOIN "Genre_s" Gs on Lt.s_id = Gs.s_id
INNER JOIN "Genre" G on G.g_id = Gs.g_id
```

### Popular Artist Rating
```postgresql
select "Rate".s_id,
       "Rate".rating,
       Ps.ar_id,
       A."Name"
from "Rate"
INNER JOIN "Produces_s" Ps on "Rate".s_id = Ps.s_id
INNER JOIN "Artist" A on A.ar_id = Ps.ar_id
```

  