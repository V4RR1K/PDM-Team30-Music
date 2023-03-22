package com.pib2000;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class intended to handle all Collection related queries
 *
 * @author Andromeda Sawtelle [andromeda.sawtelle@gmail.com]
 * @author Roshan Nunna
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

    public static void addAlbumToCollection(int u_id, int c_id, int al_id) {
        try (StarbugConnection cs = new StarbugConnection();
                Statement stmt = cs.conn.createStatement()) {
            String query = "insert into \"Song_in_collection\" (SELECT " + c_id + ", " + u_id + ",  sia.s_id from \"Song_in_album\" sia "
                + "where sia.al_id = "  + al_id + ")";
            int rs = stmt.executeUpdate(query);
        } catch (Exception e) {
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

        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = DbDriver.getInstance().conn.createStatement();
            String query = "select c_id from \"Collection\" where c_id = " + c_id + " and u_id = " + u_id;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int rs_c_id = rs.getInt("c_id");
                if(c_id == rs_c_id){
                    System.out.println(name + " already exists!");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
        }

        int result = DbDriver.getInstance().doUpdate("insert into " +
                "\"Collection\" values (" + c_id + ", " + u_id + ", \'" + name + "\')");

        if(result > 0){
            System.out.println(name + " successfully added");
        }
        else{
            System.out.println("Error in adding " + name);
        }
    }

    public static void deleteCollection(int u_id, String name){
        if(u_id < 0){
            System.out.println("Not logged in. Login/create an account");
            return;
        }
        int result = DbDriver.getInstance().doUpdate("delete from " +
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

    public static void editCollection(int u_id, String name, String new_name){
        if(u_id < 0){
            System.out.println("Not logged in. Login/create an account");
            return;
        }

        int new_c_id = new_name.hashCode() + u_id;

        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = DbDriver.getInstance().conn.createStatement();
            String query = "select c_id from \"Collection\" where c_id = " + new_c_id + " and u_id = " + u_id;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int rs_c_id = rs.getInt("c_id");
                if(new_c_id == rs_c_id){
                    System.out.println(new_name + " already exists!");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
        }

        int result = DbDriver.getInstance().doUpdate("update \"Collection\" set collection_name = \'" +
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
}