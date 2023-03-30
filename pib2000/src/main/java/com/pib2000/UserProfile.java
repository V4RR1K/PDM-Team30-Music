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
        return null;
    }
    // Roshan
    public static String topTenArtistsByCollection(int userID){
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
