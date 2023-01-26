# Design Requirements

### Multiple users can be managed by your application system. You must stored at least the following information about each user:

– Username 
– Password
– First Name 
– Last Name 
– Email
– Creation date
– Last access date

### You must store at least the following information about songs and each song must be uniquely identifiable: 

– Title
– Artist(s)
– Length
– Genre
– Release Date

### You must store at least the following information about albums and each album must be uniquely identifiable:

– Name
– Artist(s)
– Genre
– Release Date

### You must store at least the following information about artists and each artist must be uniquely identifiable:

– Name

### There also exists a relationship between songs and albums as follows:

– Every song must appear on an album.
– Each song on an album has a tack number.
– A song may appear on multiple albums with different track numbers.

### You must be able to store the following actions performed by users:

– Users must be able to record when a song is listened to; this must include date and time.
– Users will be able to give a star rating to each song they listen to (1-5). A star rating is the same no matter how many times the song is listened to.
– Users will be able to create collections of songs. Each collection must have a name.