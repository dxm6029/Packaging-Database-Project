package UI;

import SQL.Customer;
import SQL.H2Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import SQL.CustomerTable;

public class User {
    private String userName;
    private String customerID;
    private H2Main main = new H2Main();
    private CustomerTable cT = new CustomerTable();

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public UIState login (Scanner kboard) {
        boolean loggedIn = false;

        System.out.println("Logging In");

        while (!loggedIn) {
            System.out.print("Username: ");
            setUserName(kboard.nextLine());

            System.out.print("Password: ");
            customerID = kboard.nextLine();

            if(H2Main.getPassword(userName,customerID)){
                String name = H2Main.getName(customerID);
                System.out.println("Welcome to 4Squared, " + name + "!");
                return UIState.CUSTOMER_HOME;
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
        String firstName;
        String lastName;
        String streetName;
        String streetNum;
        String aptNum;
        String city;
        String state;
        String zip;
        String country;
        String email;

        int password; // also acts as customer ID

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

        System.out.print("Country: ");
        country = kboard.nextLine();

        System.out.print("Email: ");
        email = kboard.nextLine();

        while (true) {
            System.out.print("Phone Number: ");
            phoneNumbers.add(kboard.nextLine());

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
