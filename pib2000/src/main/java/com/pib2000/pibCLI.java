package com.pib2000;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pibCLI {

    public pibCLI(){
        System.out.println("Welcome to pib2000 music");
    }

    private void userLogin(){
        // Request user/pass
        System.out.println("Username: ");
        System.out.println("Password: ");

        // Add user login time
        Date currTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(currTime);

        // yyyy-mm-dd: lastaccessdate or creationdate
        // If user created an account record the time they created
    }
    private void collectionMenu() throws IOException{
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
    }
}
