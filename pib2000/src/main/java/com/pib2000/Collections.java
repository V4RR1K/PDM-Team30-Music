package com.pib2000;

import java.sql.SQLException;

/**
 * Class intended to handle all Collection related queries
 *
 * @author Andromeda Sawtelle [andromeda.sawtelle@gmail.com]
 */

public class Collections{
    public static void addCollection(int u_id, String name) {
        System.out.println("Adding collection");
        DbDriver.getInstance().doQuery("");
    }

    public static void deleteCollection(int u_id, String name){
        System.out.println("Deleting collection");
        DbDriver.getInstance().doQuery("");
    }

    public static void editCollection(int u_id, String name, String new_name){
        System.out.println("Editing collection");
        DbDriver.getInstance().doQuery("");
    }
}