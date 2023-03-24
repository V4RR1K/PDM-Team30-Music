package com.pib2000;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class intended to handle all Follower related queries
 *
 * @author Merone Delnesa [mtd6620@rit.edu]
 */

public class Following {
    int u_id;
    int follows;

    public Following(int user_id, int following_id){
        this.u_id = user_id;
        this.follows = following_id;
    }

    public static int getFollowingID(int user_id){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select follows from \"Following\" where u_id = " + user_id;
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                return rs.getInt("follows");
            }
            return -1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    public static String getFriendUsername(int friend_id){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select username from \"User\" where u_id = " + friend_id;
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                return rs.getString("username");
            }
            return "";
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String getFriendFullName (int friend_id){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select firstname, lastname from \"User\" where u_id = " + friend_id;
            ResultSet rs = cs.doQuery(query);
            if(rs.next()){
                String first = rs.getString("firstname");
                String last = rs.getString("lastname");
                return first + " " + last;
            }
            return "";
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static void addUserToFollowing(int user_id, int following_id){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "insert into \"Following\" (SELECT " + user_id + ", " + following_id;
            int rs = cs.doUpdate(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void removeUserFromFollowing(int user_id, int following_id){
        try (StarbugConnection cs = new StarbugConnection ()){
            String query = "delete from \"Following\" where u_id = " + user_id + " and follows = " + following_id;
            int rs = cs.doUpdate(query);
            if (rs > 0){
                System.out.println("User successfully deleted from Following list");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void displayFriends(int user_id){
        if (user_id < 0) {
            System.out.println("Not logged in. Login/create an account");
            return;
        }
        ResultSet rs = null;
        Map<Integer, String> friendsMap = new LinkedHashMap<>();
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select follows from \"Following\" where u_id = " + user_id;
            rs = cs.doQuery(query);
            while(rs.next()){
                int friend_id = rs.getInt("follows");
                String friendName = getFriendUsername(friend_id);
                friendsMap.put(friend_id, friendName);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        for(int i: friendsMap.keySet()){
            int currentFriendID = i;
            String currentFriendName = friendsMap.get(currentFriendID);
            System.out.println("Username: " + currentFriendName + ", ID: " + currentFriendID);
        }
    }

    public static void searchFriend(String email){
        try (StarbugConnection cs = new StarbugConnection()){
            String query = "select u_id from \"User\" where email = " + email;
            ResultSet rs = cs.doQuery(query);
            int friendID = rs.getInt("u_id");

            // print info about friend
            System.out.println("Username: " + getFriendUsername(friendID));
            System.out.println("ID: " + friendID);
            System.out.println("Name: " + getFriendFullName(friendID));
            System.out.println("Email: " + email);

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
