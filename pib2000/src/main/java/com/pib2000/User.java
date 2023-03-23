package com.pib2000;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * User models out the user queries
 * @author greglynskey gcl5615
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Date creationDate;
    private Date lastAccess;

    private final String[] column_names = {"u_id",
            "username",
            "password",
            "firstname",
            "lastname",
            "email",
            "creationdate",
            "lastaccessdate"
    };

    public User(int id){
        loadUser(id);
    }

    public User(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        createUser(this.username, this.password, this.firstname, this.lastname, this.email);
    }
    private Date generateDate(){
        Date d = new Date();
        return d;
    }

    private String generateDateString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private int nextId(){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "SELECT MAX(u_id) FROM \"User\"";
            ResultSet rs = connection.doQuery(query);
            if (rs.next()){
                return 1 + rs.getInt(1);
            }
            return -1;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public User createUser(String username, String password, String firstname, String lastname, String email){
        this.id = nextId();
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.creationDate = generateDate();

        String query = "INSERT INTO \"User\"("
                + column_names[0] + ", "
                + column_names[1] + ", "
                + column_names[2] + ", "
                + column_names[3] + ", "
                + column_names[4] + ", "
                + column_names[5] + ", "
                + column_names[6] + ") "
                + "VALUES("
                + this.nextId() + ", "
                + "\"" + this.username + "\"" + ", "
                + "\"" + this.password + "\"" +  ", "
                + "\"" + this.firstname + "\"" + ", "
                + "\"" + this.lastname + "\"" + ", "
                + "\"" + this.email + "\"" + ", "
                + generateDateString(this.creationDate) + ");";

        System.out.println(query);

        try (StarbugConnection connection = new StarbugConnection()){
            connection.doUpdate(query);
            return this;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public User updateUser(int id, User user){
        try (StarbugConnection connection = new StarbugConnection()){
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void loadUser(int id ){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "SELECT * FROM \"User\" WHERE u_id = " + id;
            ResultSet result = connection.doQuery(query);
            if (result.next()){
                // Set User variables
                this.id = result.getInt(column_names[0]);
                this.username = result.getString(column_names[1]);
                this.password = result.getString(column_names[2]);
                this.firstname = result.getString(column_names[3]);
                this.lastname = result.getString(column_names[4]);
                this.email = result.getString(column_names[5]);
                this.creationDate = result.getDate(column_names[6]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean deleteUser( int id ){
        try (StarbugConnection connection = new StarbugConnection()){
            String query = "DELETE * FROM \"User\" WHERE u_id = " + id;
            connection.doQuery(query);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(){
        this.lastAccess = generateDate();
        updateUser(this.id, this);
        return true;
    }

    @Override
    public String toString() {
        return "User[" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastAccess='" + lastAccess + '\'' +
                ']';
    }

    // User testing
    public static void main(String[] args){
//        User existing = new User(1);
//        System.out.println(existing);
        User newUser = new User("GregLynskey", "fish",
                "Greg", "Lynskey", "gcl5615@rit.edu");


    }
}
