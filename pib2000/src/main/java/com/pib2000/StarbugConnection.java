package com.pib2000;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.util.Properties;

/**
 * StarbugConnection is a class representing a single connection to starbug. It is an attempt ot make a
 * better version of DbDriver.
 *
 * When used within a try-with-resources block, it will close all connections automatically.
 *
 * @author Roshan Nunna
 */

public class StarbugConnection implements AutoCloseable {
    public Connection conn = null;
    public Session session = null;
    public ResultSet rs = null;
    public Statement stmt = null;


    public ResultSet doQuery(String query) {
        try  {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int doUpdate(String query) {
        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //beans
    public StarbugConnection() {
        int lport = 54390;
        String rhost = "starbug.cs.rit.edu";
        int rport = 5432;
        String user = "***REMOVED***"; //change to your username
        String password = "***REMOVED***"; //change to your password
        String databaseName = "***REMOVED***"; //change to your database name
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

            //System.out.println("database Url: " + url);
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
        System.out.println("Closing open connections");
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
