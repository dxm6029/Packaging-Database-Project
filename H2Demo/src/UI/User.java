package UI;

import SQL.H2Main;

import java.util.Scanner;

public class User {
    private String userName;
    private String customerID;

    public UIState login (Scanner kboard) {
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Username: ");
            userName = kboard.nextLine();

            System.out.print("Password: ");
            customerID = kboard.nextLine();

            if(H2Main.getPassword(userName).equals(customerID)){
                System.out.println("Welcome to 4Squared!");
            }else{
                System.out.println("USERNAME AND/OR PASSWORD INVALID");
            }



            // TODO: Check login credentials in SQL

            // TODO: implement way to cancel
            loggedIn = true;
        }

        return UIState.UNKNOWN_USER_HOME;
    }

    public UIState register(Scanner kboard) {
        return UIState.UNKNOWN_USER_HOME;
    }

    public UIState logout() {
        return UIState.UNKNOWN_USER_HOME;
    }
}
