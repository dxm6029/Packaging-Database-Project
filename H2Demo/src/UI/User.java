package UI;

import SQL.CustomerTable;
import SQL.Customer;
import SQL.H2Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import SQL.CustomerTable;

public class User {
    private String userName;
    private CustomerTable cT = new CustomerTable();
    private String userId;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public UIState login (Scanner kboard) {
        boolean loggedIn = false;
        UIState expected = UIState.UNKNOWN_USER_HOME;

        System.out.println("Logging In (Leave the 'Username' field empty to cancel)");

        while (!loggedIn) {
            System.out.print("Username: ");
            setUserName(kboard.nextLine());

            if (userName.length() == 0) {
                expected = UIState.UNKNOWN_USER_HOME;
                break;
            }
            else if (userName.equalsIgnoreCase("ADMIN")) {
                expected = UIState.ADMIN;
            }
            else if (userName.contains("@")) {
                expected = UIState.CUSTOMER_HOME;
            }
            else if (userName.matches("\\d\\d\\d\\d\\d")) {
                expected = UIState.DRIVER_HOME;
            }
            else {
                expected = UIState.WORKER_HOME;
            }

            System.out.print("Password: ");
            userId = kboard.nextLine();

            if (expected == UIState.ADMIN && userId.equals("ADMIN")) {
                System.out.println("Welcome Database Admin!");
                loggedIn = true;
            }
            else if (H2Main.getPassword(userName, userId, expected)) {
                if (expected == UIState.CUSTOMER_HOME) {
                    System.out.println("Welcome to Four Squared, " + H2Main.getCustomerName(userId));
                } else if (expected == UIState.WORKER_HOME) {
                    System.out.println("Welcome to Four Squared, " + H2Main.getWorkerName(userId));
                } else {
                    System.out.println("Welcome to Four Squared, Driver of Transport: " + userName);
                }
                loggedIn = true;
            } else{
                System.out.println("USERNAME AND/OR PASSWORD INVALID");
            }
        }

        return expected;
    }


    public UIState register(Scanner kboard) {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        ArrayList<String> inputs = new ArrayList<>();

        String[] fields = {"First Name: ", "Last Name: ", "Street Number: ", "Street Name: ", "Apt. Number: ",
                "City: ", "State: ", "Zip Code: ", "Country: ", "Email: "};
        int n = 0;
        String current;

        System.out.println("Registering New User (Enter 'QUIT' to quit):");
        while (n < fields.length) {
            System.out.print(fields[n]);
            current = kboard.nextLine();

            if (current.equalsIgnoreCase("QUIT")) {
                return UIState.UNKNOWN_USER_HOME;
            }
            else if (current.length() == 0 && n != 4) {
                System.out.println("This field must not be empty.");
                continue;
            }
            else if (n == 9) {
                if (!current.contains("@")) {
                    System.out.println("Invalid Email");
                    continue;
                }
                if (!CustomerTable.checkUniqueEmail(current, H2Main.getConnection())) {
                    System.out.println("User with this email already exists.");
                    continue;
                }
            }

            inputs.add(current);
            n++;
        }

        while (true) {
            System.out.print("Phone Number (10-Digits): ");
            String number = kboard.nextLine();

            if (number.equalsIgnoreCase("QUIT")) {
                return UIState.UNKNOWN_USER_HOME;
            }
            else if (number.length() != 10) {
                System.out.println("Invalid Phone Number");
                continue;
            }

            phoneNumbers.add(number);
            System.out.print("Add another phone number? (Y/N): ");
            switch (kboard.nextLine().toUpperCase()) {
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

        String firstName = inputs.get(0);
        String lastName = inputs.get(1);
        String streetNum = inputs.get(2);
        String streetName = inputs.get(3);
        String aptNum = inputs.get(4);
        String city = inputs.get(5);
        String state = inputs.get(6);
        String zip = inputs.get(7);
        String country = inputs.get(8);
        String email = inputs.get(9);

        int password; // also acts as customer ID
        Random rand = new Random();
        password = rand.nextInt(49999) + 50000; // needs to be a random num not in the DB, will act as the password - 5 digits

        String query = String.format("SELECT * FROM customer WHERE customerID = %d;", password);

        try {
            /**
             * create and execute the query
             */

            Connection conn;

            String location = "./h2demo/h2demo";
            String user = "me";
            String password2 = "password";

            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url,
                    user,
                    password2);

            Statement stmt =  conn.createStatement(); // null???

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){ // checks if the table is empty, if not enters here
                password = rand.nextInt(49999) + 50000;
                query = String.format("SELECT * FROM customer WHERE customerID = %d;", password);
                rs = stmt.executeQuery(query);
            }

            cT.addCustomer(conn, firstName, lastName, password, email, Integer.parseInt(streetNum), streetName, aptNum, city, state, country, zip);
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }

        System.out.println("New User Registered. Welcome!");
        System.out.println("Your password is: '" + password + "' Don't Forget This!");

        return this.login(kboard);
    }

    public UIState logout() {
        return UIState.UNKNOWN_USER_HOME;
    }
}
