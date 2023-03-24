package com.pib2000;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pibCLI {
    int u_id ;

    public pibCLI() throws IOException{
        System.out.println("Welcome to pib2000 music");
    }

    public void userLoginMenu() throws IOException{
        System.out.println("");
        char query;
        boolean running = true;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (running){
            System.out.print("Search > ");
            String input = r.readLine();
            if (input.length() >= 1){
                query = input.charAt(0);

                switch(query){
                    case '1':
                        System.out.println("Login");
                        userLogin();
                        break;
                    case '2':
                        System.out.println("Create Account");
                        userCreate();
                        break;
                    case 'q':
                        running = false;
                        break;
                    default:
                            System.out.println("Please input a valid entry or press q to quit");
                        break;
                }
            }
        }
    }
    private void userLogin() throws IOException{

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        // Request user/pass
        System.out.print("Username> ");
        String username = r.readLine();
        System.out.println("Password> ");
        String password = r.readLine();

        // Query for username and password
        int loginResult = User.validateCredentials(username, password);
        if (loginResult >= 0){
            this.u_id = loginResult;
        } else {
            System.out.println("Invalid Credentials, please try again or create an account");
        }
    }

    private User userCreate() throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        // Request user/pass
        System.out.print("Username > ");
        String username = r.readLine();
        System.out.println("Password > ");
        String password = r.readLine();
        System.out.println("Firstname > ");
        String firstname = r.readLine();
        System.out.println("Lastname > ");
        String lastname = r.readLine();
        System.out.println("Email > ");
        String email = r.readLine();

        return new User (username, password, firstname, lastname, email);
    }
    private void collectionMenu() throws IOException {
        System.out.println("Welcome to the collection menu");
        char query;
        boolean running = true;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (running){
            System.out.print("Collections > ");
            String input = r.readLine();
            if (input.length() >= 1){
                query = input.charAt(0);
                // Get user input
                switch(query){
                    case '1': // View Collections (Must show (Name, NumSongs, TotalDuration)
                        System.out.println("View");
                        break;
                    case '2': // Edit Collections (Edit Name, delete, add, delete, add song, add album, delete song, delete album)
                        System.out.println("Editing");
                        boolean edit_running = true;
                        while(edit_running){
                            System.out.print("Collections Edit > ");
                            String edit_input = r.readLine();
                            String edit_cmd[] = edit_input.split(" ");
                            if(edit_cmd.length >= 1){
                                switch (edit_cmd[0]){
                                    case "add":
                                        if(edit_cmd.length < 2){
                                            System.out.println("Usage: add [name]");
                                            System.out.println("Please input a valid command or press \'h\' to view menu options");
                                            break;
                                        }
                                        Collections.addCollection(u_id, edit_cmd[1]);
                                        break;
                                    case "del":
                                        if(edit_cmd.length < 2){
                                            System.out.println("Usage: del [name]");
                                            System.out.println("Please input a valid command or press \'h\' to view menu options");
                                            break;
                                        }
                                        Collections.deleteCollection(u_id, edit_cmd[1]);
                                        break;
                                    case "edit":
                                        if(edit_cmd.length < 3){
                                            System.out.println("Usage: edit [name][new name]");
                                            System.out.println("Please input a valid command or press \'h\' to view menu options");
                                            break;
                                        }
                                        Collections.editCollection(u_id, edit_cmd[1], edit_cmd[2]);
                                        break;
                                    case "addAlbum":
                                        if (edit_cmd.length < 2) {
                                            System.out.println("Usage: addAlbum [Collection_Name] [Album_Name]");
                                        }
                                        Collections.addAlbumToCollection(u_id, edit_cmd[1], edit_cmd[2]);
                                        break;
                                    case "addSong":
                                        if (edit_cmd.length < 2) {
                                            System.out.println("Usage: addSong [Collection_Name] [Song_Name]");
                                        }
                                        Collections.addSongToCollection(u_id, edit_cmd[1], edit_cmd[2]);
                                        break;
                                    case "delAlbum":
                                        if (edit_cmd.length < 2) {
                                            System.out.println("Usage: delAlbum [Collection_Name] [Album_Name]");
                                        }
                                        Collections.removeAlbumFromCollection(u_id, edit_cmd[1], edit_cmd[2]);
                                        break;
                                    case "delSong":
                                        if (edit_cmd.length < 2) {
                                            System.out.println("Usage: delSong [Collection_Name] [Song_Name]");
                                        }
                                        Collections.removeSongFromCollection(u_id, edit_cmd[1], edit_cmd[2]);
                                        break;
                                    case "h":
                                        collectionEditHelpMessage();
                                        break;
                                    case "q":
                                        edit_running = false;
                                        break;
                                    default:
                                        System.out.println("Please input a valid or press \'h\' to view menu options");
                                        break;
                                }
                            }
                        }
                        break;
                    case '3': // Play Collection (Play song or play entire collection)
                        System.out.println("Play");
                        break;
                    case 'h':
                        collectionHelpMessage();
                        break;
                    case 'q':
                        running = false;
                        break;
                    default:
                        System.out.println("Please input a valid or press \'h\' to view menu options");
                        break;
                }
            }
        }
    }



    private void searchMenu() throws IOException{
        System.out.println("Welcome to the search menu");
        char query;
        boolean running = true;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (running){
            System.out.print("Search > ");
            String input = r.readLine();
            if (input.length() >= 1){
                query = input.charAt(0);
                // Get user input
                switch(query){ // Search must include song’s name, the artist’s name, the album, the length and the listen count
                    case '1': // Search Artists
                        System.out.println("Search Artists");
                        break;
                    case '2': // Search Album
                        System.out.println("Search Albums");
                        break;
                    case '3': // Search Songs
                        System.out.println("Search Songs");
                        break;
                    case 'h':
                        searchHelpMessage();
                        break;
                    case 'q':
                        running = false;
                        break;
                    default:
                        System.out.println("Please input a valid or press \'h\' to view menu options");
                        break;
                }
            }
        }
    }

    private void friendsMenu() throws IOException{
        char query;
        boolean running = true;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (running){
            System.out.print("Friends > ");
            String input = r.readLine();
            if (input.length() >= 1){
                query = input.charAt(0);
                // Get user input
                switch(query){
                    case '1': // View Friends
                        System.out.println("View");
                        break;
                    case '2': // Search Friend
                        System.out.println("Search Friend");
                        break;
                    case '3': // Remove Friend
                        System.out.println("Remove Friend");
                        break;
                    case 'h':
                        friendHelpMessage();
                        break;
                    case 'q':
                        running = false;
                        break;
                    default:
                        System.out.println("Please input a valid or press \'h\' to view menu options");
                        break;
                }
            }
        }
    }

    private void helpMessage(){
        System.out.println( "1 - Collections\n" +
                "2 - Search\n" +
                "3 - Friends\n" +
                "h - Help\n"+
                "q - Quit");
    }

    private void collectionHelpMessage(){
        System.out.println( "1 - ViewCollections\n" +
                "2 - Edit Collections\n" +
                "3 - PlayCollections\n" +
                "h - Help\n"+
                "q - Quit");
    }

    private void collectionEditHelpMessage(){
        System.out.println(
                "add [name] - Add Collection\n" +
                "del [name] - Delete Collection\n" +
                "edit [name][new name] - Edit Collection Name\n" +
                "h - Help\n"+
                "q - Quit");
    }

    private void searchHelpMessage(){
        System.out.println( "1 - Search Artists\n" +
                "2 - Search Albums\n" +
                "3 - Search Songs\n" +
                "h - Help\n"+
                "q - Quit");
    }

    private void friendHelpMessage(){
        System.out.println( "1 - View Friends\n" +
                "2 - Search Friends\n" +
                "3 - Remove Friends\n" +
                "h - Help\n"+
                "q - Quit");
    }
    private void play(String song){
        // Record what song is played
    }

    public void run() throws IOException {
        char query;
        boolean running = true;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (running){
            System.out.print("> ");
            String input = r.readLine();
            if (input.length() >= 1){
                query = input.charAt(0);
                // Get user input
                switch(query){
                    case '1':
                        collectionMenu();
                        break;
                    case '2':
                        searchMenu();
                        break;
                    case '3':
                        friendsMenu();
                        break;
                    case 'h':
                        helpMessage();
                        break;
                    case 'q':
                        running = false;
                        break;
                    default:
                        System.out.println("Please input a valid or press \'h\' to view menu options");
                        break;
                }
            }
        }
        if (DbDriver.getInstance().conn != null) try {DbDriver.getInstance().conn.close(); } catch (SQLException ignore) {}
        if (DbDriver.getInstance().session != null) DbDriver.getInstance().session.disconnect();
    }
}