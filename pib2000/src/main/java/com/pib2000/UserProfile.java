package com.pib2000;

import java.sql.ResultSet;

public class UserProfile {
    // Andromeda
    public static String numCollections(int userID){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "meow";
            return "mwoe";
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
    // Greg
    public static int numFollowers(int userID){
        try (StarbugConnection connection = new StarbugConnection()){
            //String query = "select follows from \"Follows\" where u_id = " + userID;
            return 0;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    // Greg
    public static int numFollowed(int userID){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "select follows from \"Follows\" where u_id = " + userID;
            ResultSet rs = connection.doQuery(query);
            return rs.getFetchSize();
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    // Roshan
    public static String topTenArtistsByPlays(int userID){
        String query = "select distinct(a.\"Name\"), count(a.ar_id)\n" +
                "from \"Listened_to\" lt\n" +
                "inner join \"Produces_s\" ps\n" +
                "on lt.s_id = ps.s_id\n" +
                "inner join \"Artist\" a\n" +
                "on ps.ar_id = a.ar_id\n" +
                "where lt.u_id = " + userID + "\n" +
                "group by a.\"Name\"\n" +
                "order by count(a.ar_id) desc\n" +
                "limit 10";
        return null;
    }
    // Roshan
    public static String topTenArtistsByCollection(int userID){
        //top 10 artists by additions to collection
        return null;
    }
    // Andromeda
    public static String topTenArtistsByPlaysAndCollection(int userID){
        return null;
    }


    public static void main(String[] args){
        System.out.println(numFollowed(2));
    }
}
