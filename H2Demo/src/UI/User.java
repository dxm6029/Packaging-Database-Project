package UI;

import SQL.H2Main;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String userName;
    private String customerID;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UIState login (Scanner kboard) {
        boolean loggedIn = false;

        System.out.println("Logging In");

        while (!loggedIn) {
            System.out.print("Username: ");
            setUserName(kboard.nextLine());

            System.out.print("Password: ");
            customerID = kboard.nextLine();

            if(H2Main.getPassword(userName).equals(customerID)){
                System.out.println("Welcome to 4Squared!");
            }else{
                System.out.println("fuck off");
            }



            // TODO: Check login credentials in SQL

            // TODO: implement way to cancel

            loggedIn = true;
        }

        return UIState.UNKNOWN_USER_HOME;
    }

    public UIState register(Scanner kboard) {
        String firstName;
        String lastName;
        String streetName;
        String streetNum;
        String aptNum;
        String city;
        String state;
        String zip;
        String email;
        String password;

        ArrayList<String> phoneNumbers = new ArrayList<>();

        System.out.println("Registering New User:");

        System.out.print("First Name: ");
        firstName = kboard.nextLine();

        System.out.print("Last Name: ");
        lastName = kboard.nextLine();

        System.out.print("Street Number: ");
        streetNum = kboard.nextLine();

        System.out.print("Street Name: ");
        streetName = kboard.nextLine();

        System.out.print("Apt. Number: ");
        aptNum = kboard.nextLine();

        System.out.print("City: ");
        city = kboard.nextLine();

        System.out.print("State: ");
        state = kboard.nextLine();

        System.out.print("Zip Code: ");
        zip = kboard.nextLine();

        System.out.print("Email: ");
        email = kboard.nextLine();

        while (true) {
            System.out.print("Phone Number: ");
            phoneNumbers.add(kboard.nextLine());

            System.out.print("Add another phone number? (Y/N): ");
            switch (kboard.nextLine()) {
                case "Y":
                    System.out.println("Adding another phone number.");
                    continue;
                case "N":
                    break;
                default:
                    System.out.println("Invalid Input. Defaulting to No.");
            }
            break;
        }

        //TODO: Register in DB
        password = ""; //TODO: Get customer id
        System.out.println("New User Registered. Welcome!");
        System.out.println("Your password is: '" + password + "' Don't Forget This!");

        return this.login(kboard);
    }

    public UIState logout() {
        return UIState.UNKNOWN_USER_HOME;
    }
}
