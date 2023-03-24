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

    public static void searchName(String name) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id)\n" +
                    "where s.name like \'%" + name + "%\'\n" +
                    "order by s.name, art.\"Name\";";
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (!rs.isBeforeFirst()) {
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
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchArtist(String artist) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id)\n" +
                    "where art.\"Name\" like \'%" + artist + "%\'\n" +
                    "order by s.name, art.\"Name\";";
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (!rs.isBeforeFirst()) {
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
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchAlbum(String album) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id)\n" +
                    "where a.albumname like \'%" + album + "%\'\n" +
                    "order by s.name, art.\"Name\";";
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (!rs.isBeforeFirst()) {
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
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchGenre(String genre) {
        ResultSet rs;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s.s_id as s_id, s.name as songname, s.length as length, a.albumname as albumname, art.\"Name\" as artistname from (\"Song\" s\n" +
                    "    left join \"Song_in_album\" sia on sia.s_id = s.s_id\n" +
                    "    left join \"Album\" a on a.al_id = sia.al_id\n" +
                    "    left join \"Produces_s\" ps on ps.s_id = s.s_id\n" +
                    "    left join \"Artist\" art on art.ar_id = ps.ar_id\n" +
                    // Need to add these two so that we can properly search
                    // by genre; genre info not needed if searching via
                    // other means but is needed for this function
                    "    left join \"Genre_s\" gs on gs.s_id = s.s_id\n" +
                    "    left join \"Genre\" g on g.g_id = gs.g_id)\n" +
                    "where g.genrename like \'%" + genre + "%\'\n" +
                    "order by s.name, art.\"Name\";";
            rs = cs.doQuery(query);

            // check if we actually got anything
            if (!rs.isBeforeFirst()) {
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
                System.out.println("Length: " + length);
                System.out.println("Listen Count: " + listen_count);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
