package com.pib2000;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile {
    // Andromeda
    public static int numCollections(int userID){
        try (StarbugConnection conn = new StarbugConnection()){
            String query = "select count(c_id)\n" +
                    "from \"Collection\"\n" +
                    "where u_id = " + userID;
            ResultSet rs = conn.doQuery(query);
            int count = 1;
            while(rs.next()){
                count = rs.getInt("count");
            }
            return count;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // Greg
    public static int numFollowers(int userID){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "select COUNT(u_id) from \"Follows\" where follows = " + userID;
            ResultSet rs = connection.doQuery(query);
            if (rs.next()){
                return rs.getInt("count");
            }
            return 0;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    // Greg
    public static int numFollowed(int userID){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "select COUNT(follows) from \"Follows\" where u_id = " + userID;
            ResultSet rs = connection.doQuery(query);
            if (rs.next()){
                return rs.getInt("count");
            }
            return 0;
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
        StringBuilder sb = new StringBuilder();
        try (StarbugConnection conn = new StarbugConnection()) {
            ResultSet rs = conn.doQuery(query);

            while (rs.next()) {
                sb.append(rs.getString("Name"));
                sb.append(" ");
                sb.append(rs.getString("count"));
                sb.append("\n");
            }

            return sb.toString();
        }
        catch (Exception e) {}
        return sb.toString();
    }
    // Roshan
    public static String topTenArtistsByCollection(int userID){
        //top 10 artists by additions to collection
        String query = "select distinct(a.\"Name\"), count(a.ar_id)\n" +
                "from \"Song_in_collection\" lt\n" +
                "inner join \"Produces_s\" ps\n" +
                "on lt.s_id = ps.s_id\n" +
                "inner join \"Artist\" a\n" +
                "on ps.ar_id = a.ar_id\n" +
                "where lt.u_id = " + userID + "\n" +
                "group by a.\"Name\"\n" +
                "order by count(a.ar_id) desc\n" +
                "limit 10";
        StringBuilder sb = new StringBuilder();
        try (StarbugConnection conn = new StarbugConnection()) {
            ResultSet rs = conn.doQuery(query);

            while (rs.next()) {
                sb.append(rs.getString("Name"));
                sb.append(" ");
                sb.append(rs.getString("count"));
                sb.append("\n");
            }
            return sb.toString();
        }
        catch (Exception e) {}
        return sb.toString();
    }
    /*
    Andromeda
    Ideas to improve:
        - Do it in one SQL query
    */
    public static String topTenArtistsByPlaysAndCollection(int userID){
        //top 10 artists by additions to collection
        String col_query = "select distinct(a.\"Name\"), count(a.ar_id)\n" +
                "from \"Song_in_collection\" lt\n" +
                "inner join \"Produces_s\" ps\n" +
                "on lt.s_id = ps.s_id\n" +
                "inner join \"Artist\" a\n" +
                "on ps.ar_id = a.ar_id\n" +
                "where lt.u_id = " + userID + "\n" +
                "group by a.\"Name\"\n" +
                "order by count(a.ar_id) desc\n";
        String play_query = "select distinct(a.\"Name\"), count(a.ar_id)\n" +
                "from \"Listened_to\" lt\n" +
                "inner join \"Produces_s\" ps\n" +
                "on lt.s_id = ps.s_id\n" +
                "inner join \"Artist\" a\n" +
                "on ps.ar_id = a.ar_id\n" +
                "where lt.u_id = " + userID + "\n" +
                "group by a.\"Name\"\n" +
                "order by count(a.ar_id) desc\n";
        HashMap<String, Integer> artist_num = new HashMap<>();
        try (StarbugConnection conn = new StarbugConnection()) {
            ResultSet rs = conn.doQuery(col_query);
            while (rs.next()) {
                String name = rs.getString("Name");
                int amnt = rs.getInt("count");
                Integer name_amnt = artist_num.get(name);
                if(artist_num.get(name) == null)
                    name_amnt = 0;
                artist_num.put(name, name_amnt + amnt);
            }

            rs = conn.doQuery(play_query);
            while (rs.next()) {
                String name = rs.getString("Name");
                int amnt = rs.getInt("count");
                Integer name_amnt = artist_num.get(name);
                if(artist_num.get(name) == null)
                    name_amnt = 0;
                artist_num.put(name, name_amnt + amnt);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(artist_num.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Integer> entry : list){
            if(count == 10)
                break;
            sb.append(entry.getKey());
            sb.append(" ");
            sb.append(entry.getValue());
            sb.append("\n");
            count++;
        }
        return sb.toString();
    }

    public static void main(String[] args){

        System.out.println(numFollowers(2));
        System.out.println();
        System.out.println(numFollowed(2));

//        System.out.println(topTenArtistsByCollection(2));
//        System.out.println();
//        System.out.println(topTenArtistsByPlays(2));
//        System.out.println();
//        System.out.println(topTenArtistsByPlaysAndCollection(2));
//
    }
}
