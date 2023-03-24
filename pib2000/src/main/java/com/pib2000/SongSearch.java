package com.pib2000;

import java.sql.ResultSet;
import java.sql.Time;

/**
 * Class intended to process search queries
 * related to searching for a song by name,
 * artist, album or genre.
 *
 * @author Lily O'Carroll [lso2973@rit.edu]
 * @author Roshan Nunna
 */
public class SongSearch {
    private final static String defaultFilter = "order by s.name, art.\"Name\"";
    private final static String songNameFilter = "order by s.name";
    private final static String artistFilter = "order by art.\"Name\"";
    private final static String albumFilter = "order by a.albumname";
    private final static String genreFilter = "order by g.genrename";


    private static String applyFilter (String query, String filterString) {
        String[] filterArr = filterString.split(" ");
        if (filterString.length() < 1 || filterArr.length < 2) {
            return applyFilter(query, "default", false);
        }

        return applyFilter(query, filterArr[0], Boolean.parseBoolean(filterArr[1]));
    }
    private static String applyFilter(String query, String filter, boolean isDesc) {
        String toAppend = "";
        switch (filter) {
            case "songName":
                toAppend = query + songNameFilter;
                break;
            case "artist":
                toAppend = query + artistFilter;
                break;
            case "album":
                toAppend = query + albumFilter;
                break;
            case "genre":
                toAppend = query + genreFilter;
                break;
            default:
                toAppend = query + defaultFilter;
                break;
        }
        if (isDesc) {
            toAppend = toAppend + " desc";
        }
        return toAppend + ";";
    }
    public static void searchName(String name, String filterString) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, g.genrename as genre, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id\n" +
                    "    left join \"Genre_s\" gs on gs.s_id = s.s_id\n" +
                    "    left join \"Genre\" g on g.g_id = gs.g_id)\n" +
                    "where s.name like \'%" + name + "%\'\n";
            query = applyFilter(query, filterString);
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (rs == null || !rs.isBeforeFirst()) {
                System.out.println("Nothing matching " + name + " was found!");
                return;
            }

            while (rs.next()) {
                // get the song's name
                String song_name = rs.getString("songname");
                Time length = rs.getTime("length");
                int s_id = rs.getInt("s_id");

                // get the album name the song is a part of
                String album_name = rs.getString("albumname");

                // get the artist name the song was made by
                String artist_name = rs.getString("artistname");

                String genre_name = rs.getString("genre");

                // get how many times the song was listened to
                int listen_count = 0;
                try (StarbugConnection cs2 = new StarbugConnection()) {
                    ResultSet getListenCount;
                    String getListenCountQuery = "select count(s_id) from \"Listened_to\"" +
                            "where s_id = " + s_id;
                    getListenCount = cs2.doQuery(getListenCountQuery);
                    if (getListenCount.next()) {
                        listen_count = getListenCount.getInt("count");
                    }
                }

                //printing stuff
                System.out.println("Name: " + song_name);
                System.out.println("Artist: " + artist_name);
                System.out.println("Album: " + album_name);
                System.out.println("Genre: " + genre_name);
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchArtist(String artist, String filterString) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, g.genrename as genre, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id\n" +
                    "    left join \"Genre_s\" gs on gs.s_id = s.s_id\n" +
                    "    left join \"Genre\" g on g.g_id = gs.g_id)\n" +
                    "where art.\"Name\" like \'%" + artist + "%\'\n";
            query = applyFilter(query, filterString);
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (rs == null || !rs.isBeforeFirst()) {
                System.out.println("Nothing matching " + artist + " was found!");
                return;
            }

            while (rs.next()) {
                // get the song's name
                String song_name = rs.getString("songname");
                Time length = rs.getTime("length");
                int s_id = rs.getInt("s_id");

                // get the album name the song is a part of
                String album_name = rs.getString("albumname");

                // get the artist name the song was made by
                String artist_name = rs.getString("artistname");

                String genre_name = rs.getString("genre");

                // get how many times the song was listened to
                int listen_count = 0;
                try (StarbugConnection cs2 = new StarbugConnection()) {
                    ResultSet getListenCount;
                    String getListenCountQuery = "select count(s_id) from \"Listened_to\"" +
                            "where s_id = " + s_id;
                    getListenCount = cs2.doQuery(getListenCountQuery);
                    if (getListenCount.next()) {
                        listen_count = getListenCount.getInt("count");
                    }
                }

                //printing stuff
                System.out.println("Name: " + song_name);
                System.out.println("Artist: " + artist_name);
                System.out.println("Album: " + album_name);
                System.out.println("Genre: " + genre_name);
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchAlbum(String album, String filterString) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, g.genrename as genre, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id\n" +
                    "    left join \"Genre_s\" gs on gs.s_id = s.s_id\n" +
                    "    left join \"Genre\" g on g.g_id = gs.g_id\n" +
                    "where a.albumname like \'%" + album + "%\'\n";
            query = applyFilter(query, filterString);
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (rs == null || !rs.isBeforeFirst()) {
                System.out.println("Nothing matching " + album + " was found!");
                return;
            }

            while (rs.next()) {
                // get the song's name
                String song_name = rs.getString("songname");
                Time length = rs.getTime("length");
                int s_id = rs.getInt("s_id");

                // get the album name the song is a part of
                String album_name = rs.getString("albumname");

                // get the artist name the song was made by
                String artist_name = rs.getString("artistname");

                String genre_name = rs.getString("genre");

                // get how many times the song was listened to
                int listen_count = 0;
                try (StarbugConnection cs2 = new StarbugConnection()) {
                    ResultSet getListenCount;
                    String getListenCountQuery = "select count(s_id) from \"Listened_to\"" +
                            "where s_id = " + s_id;
                    getListenCount = cs2.doQuery(getListenCountQuery);
                    if (getListenCount.next()) {
                        listen_count = getListenCount.getInt("count");
                    }
                }

                //printing stuff
                System.out.println("Name: " + song_name);
                System.out.println("Artist: " + artist_name);
                System.out.println("Album: " + album_name);
                System.out.println("Genre: " + genre_name);
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchGenre(String genre, String filterString) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, g.genrename as genre, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id\n" +
                    "    left join \"Genre_s\" gs on gs.s_id = s.s_id\n" +
                    "    left join \"Genre\" g on g.g_id = gs.g_id)\n" +
                    "where g.genrename like \'%" + genre + "%\'\n";
            query = applyFilter(query, filterString);
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (rs == null || !rs.isBeforeFirst()) {
                System.out.println("Nothing matching " + genre + " was found!");
                return;
            }

            while (rs.next()) {
                // get the song's name
                String song_name = rs.getString("songname");
                Time length = rs.getTime("length");
                int s_id = rs.getInt("s_id");

                // get the album name the song is a part of
                String album_name = rs.getString("albumname");

                // get the artist name the song was made by
                String artist_name = rs.getString("artistname");

                String genre_name = rs.getString("genre");
                // get how many times the song was listened to
                int listen_count = 0;
                try (StarbugConnection cs2 = new StarbugConnection()) {
                    ResultSet getListenCount;
                    String getListenCountQuery = "select count(s_id) from \"Listened_to\"" +
                            "where s_id = " + s_id;
                    getListenCount = cs2.doQuery(getListenCountQuery);
                    if (getListenCount.next()) {
                        listen_count = getListenCount.getInt("count");
                    }
                }

                //printing stuff
                System.out.println("Name: " + song_name);
                System.out.println("Artist: " + artist_name);
                System.out.println("Album: " + album_name);
                System.out.println("Genre: " + genre_name);
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
