package com.pib2000;

public class UserProfile {
    // Greg
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
            String query = "meow";
            return 0;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    // Greg
    public static int numFollowed(int userID){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "meow";
            return 0;
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
    // Roshan
    public static String topTenArtistsByPlaysAndCollection(int userID){
        return null;
    }
}
