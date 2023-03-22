package com.pib2000;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.util.Properties;

/**
 * StarbugConnection is a class representing a single connection to starbug. It is an attempt ot make a
 * better version of DbDriver.
 *
 * @author Roshan Nunna
 */
public class StarbugConnection implements AutoCloseable {
    Connection conn = null;
    Session session = null;


    public ResultSet doQuery(String query) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int doUpdate(String query) {
        try (Statement stmt = conn.createStatement()) {
            int rs = stmt.executeUpdate(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //beans
    public StarbugConnection() throws SQLException {
        int lport = 54390;
        String rhost = "starbug.cs.rit.edu";
        int rport = 5432;
        String user = System.getenv("STARUSER"); //change to your username
        String password = System.getenv("STARPASS"); //change to your password
        String databaseName = System.getenv("DBNAME"); //change to your database name
        String driverName = "org.postgresql.Driver";
        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, rhost, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
            session.connect();
            System.out.println("Connected");
            int assigned_port = session.setPortForwardingL(lport, "localhost", rport);
            System.out.println("Port Forwarded");

            // Assigned port could be different from 5432 but rarely happens
            String url = "jdbc:postgresql://localhost:"+ assigned_port + "/" + databaseName;

            System.out.println("database Url: " + url);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);

            Class.forName(driverName);
            conn = DriverManager.getConnection(url, props);
            System.out.println("Database connection established");
            // Do something with the database....
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() throws Exception {
        conn.close();
        session.disconnect();
    }
}
