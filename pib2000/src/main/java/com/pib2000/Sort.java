package com.pib2000;

import java.sql.ResultSet;

/**
 * Class intended to handle all sorting queries
 *
 * @author Merone Delnesa
 */

public class Sort {
    String song_name;
    String artist_name;
    int s_id;
    int release_date;

    public Sort(String s_name, String a_name, int songID, int releaseDate){
        this.song_name = s_name;
        this.artist_name = a_name;
        this.s_id = songID;
        this.release_date = releaseDate;
    }


    public static int getAlbumID(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select al_id from \"Song_in_album\" where s_id = " + songID;
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                return rs.getInt("al_id");
            }
            return -1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public static int getArtistID(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select ar_id from \"Produces_s\" where s_id = " + songID;
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                return rs.getInt("ar_id");
            }
            return -1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public static String getArtistName(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Artist\" where ar_id = " + getArtistID(songID);
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                return rs.getString("name");
            }
            return "";
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static void sortSongNameASC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" order by name ASC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortSongNameDESC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" order by name DESC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortSongGenreASC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" s inner join \"Genre_s\" gs on  s.s_id =  gs.s_id inner join " +
                    "\"Genre\" g on gs.s_id = g.s_id order by g.genrename ASC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortSongGenreDESC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" s inner join \"Genre_s\" gs on  s.s_id =  gs.s_id inner join " +
                    "\"Genre\" g on gs.s_id = g.s_id order by g.genrename DESC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortSongAristASC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" s inner join \"Produces_s\" p on  s.s_id =  p.s_id inner join " +
                    "\"Artist\" a on p.ar_id = a.ar_id order by a.Name ASC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortSongAristDESC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" s inner join \"Produces_s\" p on  s.s_id =  p.s_id inner join " +
                    "\"Artist\" a on p.ar_id = a.ar_id order by a.Name DESC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortSongReleaseASC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" s inner join \"Released_On\" r on  s.s_id =  r.s_id order by " +
                    "r.release ASC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sortSongReleaseDESC(int songID){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select name from \"Song\" s inner join \"Released_On\" r on  s.s_id =  r.s_id order by " +
                    "r.release DESC";
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                rs.getString("name");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
