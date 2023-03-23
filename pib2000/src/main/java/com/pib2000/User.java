package com.pib2000;

import java.text.SimpleDateFormat;
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
    private String creationDate;
    private String lastAccess;

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
    private String generateDate(){
        Date currTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(currTime);
    }

    private int nextId(){
        try (StarbugConnection connection = new StarbugConnection()){
            return 0;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public User createUser(String username, String password, String firstname, String lastname, String email){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.creationDate = this.generateDate();

        // Manually generate create date, login date
        try (StarbugConnection connection = new StarbugConnection()){
            return null;
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

    public User loadUser( int id ){
        try (StarbugConnection connection = new StarbugConnection()){
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteUser( int id ){
        try (StarbugConnection connection = new StarbugConnection()){
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(){
        // Update last login date
        return true;
    }
}
