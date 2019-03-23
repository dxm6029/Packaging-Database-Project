package UI;

import java.util.Scanner;

public class User {
    private String userName;

    public UIState login (Scanner kboard) {
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Username: ");
            userName = kboard.nextLine();

            System.out.print("Password: ");
            String pass = kboard.nextLine();

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
