package com.pib2000;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

/**
 * Class intended to handle all Collection related queries
 *
 * @author Andromeda Sawtelle [andromeda.sawtelle@gmail.com]
 * @author Roshan Nunna
 * @author Lily O'Carroll [lso2973@rit.edu]
 */

public class Collections{
    int c_id;
    int u_id;
    String collection_name;

    public Collections(int c_id, int u_id, String collection_name){
        this.c_id = c_id;
        this.u_id = u_id;
        this.collection_name = collection_name;
    }

    private static int getCollectionId(int u_id, String collectionName) {
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select c_id from \"Collection\" where u_id = " + u_id + " and collection_name = \'" + collectionName + "\'";
            ResultSet rs = cs.doQuery(query);
            if (rs.next()) {
                return rs.getInt("c_id");
            }
            return -1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int getAlbumId(String albumName) {
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select al_id from \"Album\" where albumname = \'" + albumName + "\'";
            ResultSet rs = cs.doQuery(query);
            if (rs.next()) {
                return rs.getInt("al_id");
            }
            return -1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int getSongId(String songName) {
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select s_id from \"Song\" where name = \'" + songName + "\'";
            ResultSet rs = cs.doQuery(query);
            if (rs.next()) {
                return rs.getInt("s_id");
            }
            return -1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void addAlbumToCollection(int u_id, String collectionName, String albumName) {
        collectionName = collectionName.replace('_', ' ');
        albumName = albumName.replace('_', ' ');
        int c_id = getCollectionId(u_id, collectionName);
        int al_id = getAlbumId(albumName);
        try (StarbugConnection cs = new StarbugConnection();) {
            String query = "insert into \"Song_in_collection\" (SELECT " + c_id + ", " + u_id + ",  sia.s_id from \"Song_in_album\" sia "
                + "where sia.al_id = "  + al_id + ")";
            int rs = cs.doUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addSongToCollection(int u_id, String collectionName, String songName) {
        collectionName = collectionName.replace('_', ' ');
        songName = songName.replace('_', ' ');
        int c_id = getCollectionId(u_id, collectionName);
        int s_id = getSongId(songName);
        try (StarbugConnection cs = new StarbugConnection();) {
            String query = "insert into \"Song_in_collection\" (SELECT " + c_id + ", " + u_id + ",  sia.s_id from \"Song\" sia "
                    + "where sia.s_id = "  + s_id + ")";
            int rs = cs.doUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeSongFromCollection(int u_id, String collectionName, String songName) {
        collectionName = collectionName.replace('_', ' ');
        songName = songName.replace('_', ' ');
        int c_id = getCollectionId(u_id, collectionName);
        int s_id = getSongId(songName);
        try (StarbugConnection cs = new StarbugConnection()) {
            int rs = cs.doUpdate("delete from " +
                    "\"Song_in_collection\" where u_id = " + u_id + " and c_id = " + c_id + "and s_id = " + s_id );
            if (rs > 0) {
                System.out.println("Song successfully deleted from collection");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeAlbumFromCollection(int u_id, String collectionName, String albumName) {
        collectionName = collectionName.replace('_', ' ');
        albumName = albumName.replace('_', ' ');
        int c_id = getCollectionId(u_id, collectionName);
        int al_id = getAlbumId(albumName);
        try (StarbugConnection cs = new StarbugConnection()) {
            int rs = cs.doUpdate("delete from " +
                    "\"Song_in_collection\" where u_id = " + u_id + " and c_id = " + c_id + " and s_id in (select s.s_id from \"Song_in_collection\" s where s.s_id = " + al_id + ")");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCollection(int u_id, String name) {
        if(u_id < 0){
            System.out.println("Not logged in. Login/create an account");
            return;
        }

        int c_id = name.hashCode() + u_id;
        if(c_id < 0)
            c_id *= -1;

        try (StarbugConnection sc = new StarbugConnection()) {
            String query = "select c_id from \"Collection\" where c_id = " + c_id + " and u_id = " + u_id;
            ResultSet rs = sc.doQuery(query);
            while (rs.next()) {
                int rs_c_id = rs.getInt("c_id");
                if(c_id == rs_c_id){
                    System.out.println(name + " already exists!");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (StarbugConnection sc = new StarbugConnection()) {
            int result = sc.doUpdate("insert into " +
                    "\"Collection\" values (" + c_id + ", " + u_id + ", \'" + name + "\')");

            if(result > 0){
                System.out.println(name + " successfully added");
            }
            else{
                System.out.println("Error in adding " + name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCollection(int u_id, String name) {
        try (StarbugConnection sc = new StarbugConnection()) {
            if(u_id < 0){
                System.out.println("Not logged in. Login/create an account");
                return;
            }
            int result = sc.doUpdate("delete from " +
                    "\"Collection\" where u_id = " + u_id + " and collection_name = \'" + name + "\'");

            if(result > 0){
                System.out.println(name + " successfully deleted");
            }
            else if(result == 0){
                System.out.println(name + " did not exist");
            }
            else{
                System.out.println("Error in deleting " + name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editCollection(int u_id, String name, String new_name){
        if(u_id < 0){
            System.out.println("Not logged in. Login/create an account");
            return;
        }

        int new_c_id = new_name.hashCode() + u_id;

        ResultSet rs = null;
        try (StarbugConnection sc = new StarbugConnection()) {
            String query = "select c_id from \"Collection\" where c_id = " + new_c_id + " and u_id = " + u_id;
            rs = sc.doQuery(query);
            while (rs.next()) {
                int rs_c_id = rs.getInt("c_id");
                if(new_c_id == rs_c_id){
                    System.out.println(new_name + " already exists!");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (StarbugConnection sc = new StarbugConnection()) {
            int result = sc.doUpdate("update \"Collection\" set collection_name = \'" +
                    new_name + "\', c_id = " + new_c_id + " where collection_name = \'" + name + "\'");

            if(result > 0){
                System.out.println(name + " successfully updated to " + new_name);
            }
            else if(result == 0){
                System.out.println(name + " did not exist");
            }
            else{
                System.out.println("Error in updating " + name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displayCollections(int u_id) {
        if (u_id < 0) {
            System.out.println("Not logged in. Login/create an account");
            return;
        }
        ResultSet rs = null;
        Map<Integer, String> collectionMap = new HashMap<>();
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select * from \"Collection\" where u_id = " + u_id;
            rs = cs.doQuery(query);
            while (rs.next()) {
                int c_id = rs.getInt("c_id");
                String collection_name = rs.getString("collection_name");
                collectionMap.put(c_id, collection_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i : collectionMap.keySet()) {
            int c_id = i;
            String collection_name = collectionMap.get(i);
            System.out.println("Collection: " + collection_name);
            int num_songs = 0;
            int duration_minutes = 0;
            int s_id = 0;
            try (StarbugConnection cs2 = new StarbugConnection()) {
                ResultSet rs_num_songs = cs2.doQuery("select count(*) from \"Song_in_collection\"" +
                        " where c_id =" + c_id);
                if (rs_num_songs.next()) {
                    num_songs = rs_num_songs.getInt("count");
                }
                else {
                    continue; //no need to count the song length if there are no songs!
                }
                System.out.println("Number of Songs: " + num_songs);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (StarbugConnection cs3 = new StarbugConnection()) {
                ResultSet rs_duration_minutes = cs3.doQuery("select extract(hour from (select sum(length)\n" +
                        "            from \"Song\" s\n" +
                        "            where s.s_id in (select s_id from \"Song_in_collection\" where c_id = " + c_id + "))) as minutes");
                if (rs_duration_minutes.next()) {
                    duration_minutes = rs_duration_minutes.getInt("minutes");
                }
                System.out.println("Duration, in Minutes: " + duration_minutes);
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void searchName(String name) {
        ResultSet rs = null;
        try (StarbugConnection cs = new StarbugConnection()) {
            String query = "select * from \"Song\" where name like %" + name + "%";
            rs = cs.doQuery(query);
            while (rs.next()) {
                // get the song's name
                String song_name = rs.getString("title");
                Time length = rs.getTime("length");
                int s_id = getSongId(name);

                // get the album name the song is a part of
                ResultSet getAlbumId;
                String getAlbumIdQuery = "select al_id from \"Song_in_Album\"" +
                        " where s_id = " + s_id;
                getAlbumId = cs.doQuery(getAlbumIdQuery);
                int albumId = getAlbumId.getInt("al_id");
                ResultSet getAlbumName;
                String getAlbumNameQuery = "select albumname from \"Album\" where"
                        + "al_id = " + albumId;
                getAlbumName = cs.doQuery(getAlbumNameQuery);
                String album_name = getAlbumName.getString("albumname");

                // get the artist name the song was made by
                ResultSet getArtistId;
                String getArtistIdQuery = "select ar_id from \"Produces_s\" where" +
                        "s_id = " + s_id;
                getArtistId = cs.doQuery(getArtistIdQuery);
                int artistId = getArtistId.getInt("ar_id");
                ResultSet getArtistName;
                String getArtistNameQuery = "select Name from \"Artist\" where" +
                        "ar_id = " + artistId;
                getArtistName = cs.doQuery(getArtistNameQuery);
                String artist_name = getArtistName.getString("Name");

                // get how many times the song was listened to
                ResultSet getListenCount;
                String getListenCountQuery = "select count (s_id) from \"Listened_to\"" +
                        "where s_id = " + s_id;
                getListenCount = cs.doQuery(getListenCountQuery);
                int listen_count = getListenCount.getInt("count");

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

    }

    public static void searchAlbum(String album) {

    }

    public static void searchGenre(String genre) {

    }
}