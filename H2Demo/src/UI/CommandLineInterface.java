package UI;

import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import SQL.*;
import SQL.Package;

public class CommandLineInterface {
    private Scanner kboard;
    private UIState state;
    private User user;
    private PackageTable packageTable;

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.runCLI();
    }

    public CommandLineInterface() {
        this.kboard = new Scanner(System.in);
        this.state = UIState.UNKNOWN_USER_HOME;
        this.user = new User();
    }

    public void runCLI() {
        String option;

        do {
            printStateInfo(state);
            ArrayList<String> options = getStateOptions(state);
            printOptions(options);
            option = kboard.nextLine();

            if (options.contains(option.toUpperCase())) {
                state = executeCommand(option, state);
            }
            else if (option.equalsIgnoreCase("QUIT")) {
                System.out.println("Goodbye");
            }
            else {
                System.out.println("Invalid command. Please enter a new one.");
            }
        }
        while (!option.equalsIgnoreCase("QUIT"));
    }

    private void printOptions(ArrayList<String> options) {
        System.out.println("Possible Commands: (Or type 'QUIT' to exit)");

        for  (String option : options) {
            System.out.println("\t\t" + option);
        }
    }

    private UIState executeCommand(String option, UIState state) {
        switch (state) {
            case UNKNOWN_USER_HOME:
                switch (option.toUpperCase()) {
                    case "TRACK":
                        System.out.print("Enter PackageID: ");
                        int packageId = inputNumber();
                        displayPackageInfo(packageId);
                        return state;
                    case "LOGIN":
                        return user.login(kboard);
                    case "REGISTER":
                        return user.register(kboard);
                }
                break;
            case CUSTOMER_HOME:
                switch (option.toUpperCase()) {
                    case "LOGOUT":
                        return user.logout();
                    case "PACKAGES":
                        return UIState.PACKAGES_LIST;
                    case "ADD":
                        addPackage();
                        return UIState.CUSTOMER_HOME;
                    case "BILLING":
                        return UIState.TRANSACTION_LIST;
                }
                break;
            case WORKER_HOME:
                switch (option.toUpperCase()) {
                    case "LOGOUT":
                        return user.logout();
                    case "SCAN":
                        scanPackages();
                        return UIState.WORKER_HOME;
                    case "DELIVER":
                        markDelivered();
                }
                break;
            case PACKAGES_LIST:
                if (option.equalsIgnoreCase("HOME")) {
                    return UIState.CUSTOMER_HOME;
                }
                else {
                    displayPackageInfo(Integer.parseInt(option));
                    return state;
                }
            case TRANSACTION_LIST:
                if (option.equalsIgnoreCase("HOME")) {
                    return UIState.CUSTOMER_HOME;
                }
                else {
                    displayTransactionInfo(Integer.parseInt(option));
                    return state;
                }
            case ADMIN:
                if (option.equalsIgnoreCase("SQL")) {
                    enterSqlStatements();
                    return state;
                }
                else if (option.equalsIgnoreCase("LOGOUT")) {
                    return UIState.UNKNOWN_USER_HOME;
                }
        }

        return state;
    }

    private void scanPackages() {
        int packageId;

        System.out.println("Scanning Packages");

        while (true) {
            System.out.print("Enter PackageID (Or -1 to cancel): ");
            packageId = inputNumber();
            if (packageId == -1) {
                break;
            }

            H2Main.scanPackage(packageId, Integer.parseInt(user.getUserId()));

            System.out.print("Enter 'Y' to scan another package or anything else to stop: ");
            if (!kboard.nextLine().equalsIgnoreCase("Y")) {
                break;
            }

            System.out.println("Scanning another package");
        }
    }

    private void markDelivered() {
        int packageId;

        System.out.println("Marking Packages Delivered");
        while (true) {
            System.out.print("Enter PackageID (Or -1 to cancel): ");
            packageId = inputNumber();
            if (packageId == -1) {
                break;
            }

            H2Main.setDelivered(packageId, Integer.parseInt(user.getUserId()));

            System.out.print("Enter 'Y' to deliver another package or anything else to stop: ");
            if (!kboard.nextLine().equalsIgnoreCase("Y")) {
                break;
            }

            System.out.println("Marking Another Package");
        }
    }

    private ArrayList<String> getStateOptions(UIState state) {
        ArrayList<String> options = new ArrayList<>();

        switch (state) {
            case UNKNOWN_USER_HOME:
                options.add("LOGIN");
                options.add("TRACK");
                options.add("REGISTER");
                break;
            case CUSTOMER_HOME:
                options.add("LOGOUT");
                options.add("PACKAGES");
                options.add("ADD");
                options.add("BILLING");
                break;
            case WORKER_HOME:
                options.add("SCAN");
                options.add("DELIVER");
                options.add("LOGOUT");
                break;
            case PACKAGES_LIST:
                ArrayList<Integer> packages = H2Main.getPackageIds(Integer.parseInt(user.getUserId()));
                for (int id: packages) {
                    options.add(Integer.toString(id));
                }
                options.add("HOME");
                break;
            case TRANSACTION_LIST:
                ArrayList<Integer> transactions = H2Main.getTransactionIds(Integer.parseInt(user.getUserId()));
                for (int id : transactions) {
                    options.add(Integer.toString(id));
                }
                options.add("HOME");
                break;
            case ADMIN:
                options.add("SQL");
                options.add("LOGOUT");
        }

        return options;
    }

    private void displayPackageInfo(int packageId) {
        Package p = H2Main.getPackageInfo(packageId);
        if (p == null) {
            System.out.println("Invalid Package ID");
        }
        else {
            System.out.println(p.toString());
        }

        System.out.print("\nPress Enter to continue.");
        kboard.nextLine();
    }

    private void displayTransactionInfo(int transactionId) {
        Transaction t = H2Main.getTransactionInfo(transactionId);
        String paymentType = H2Main.getTransactionPaymentType(transactionId);

        System.out.println("\n" + t.toString());
        System.out.println("Payment Type Used: " + paymentType + "\n");

        System.out.print("Press Enter to continue.");
        kboard.nextLine();
    }

    public int inputNumber() {
        while (!kboard.hasNextInt()) {
            try {
                kboard.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                kboard.nextLine();
            }
        }

        int num = kboard.nextInt();
        kboard.nextLine();

        return num;
    }

    private int nextStep(int currentStep) {
        System.out.print("Type 'Q' to quit, 'R' to redo, 'B' to go back, or anything else to continue: ");
        String option = kboard.nextLine();

        switch (option.toUpperCase()) {
            case "Q":
                return 0;
            case "R":
                return currentStep;
            case "B":
                return currentStep - 1;
            default:
                return currentStep + 1;
        }
    }

    private void addPackage() {
        int stepNum = 1;

        // Package Info
        double weight = -1;
        String packageType = "";
        String deliveryType = ""; // 1-day, overnight, 3-5 day, 7+ day
        int packID = 0;
        String locate = "";
        String startedDelivery = ""; // today's date
        String extraInfo = ""; // fragile, hazardous, N/A
        int transactionID = 0;

        // Recipient Info
        String firstName = "";
        String lastName = "";
        String streetName = "";
        String streetNum = "";
        String aptNum = "";
        String city = "";
        String state = "";
        String country = "";
        String zip = "";

        // Payment Info
        String paymentType = "";
        // If credit card
        String cardholderName = "";
        int cardNumber = -1;
        int expMonth = -1;
        int expYear = -1;
        int cvv = -1;

        while (stepNum < 4) {
            switch (stepNum) {
                case 0:
                    return;
                case 1:
                    System.out.print("Enter Package Weight: ");

                    while (!kboard.hasNextDouble()) {
                        try {
                            kboard.nextDouble();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please enter a number.");
                            kboard.nextLine();
                        }
                    }
                    weight = kboard.nextDouble();
                    kboard.nextLine(); // Need to consume newline before continuing

                    System.out.println("Package Weight Entered");
                    System.out.print("Type 'Q' to quit, 'R' to redo, 'B' to go back, or anything else to continue: ");
                    String option = kboard.nextLine();

                    switch (option.toUpperCase()) {
                        case "Q":
                            return;
                        case "R":
                            break;
                        case "B":
                            stepNum = stepNum - 1;
                            break;
                        default:
                            stepNum = stepNum + 1;
                    }

                    ArrayList<String> packageTypes = new ArrayList<>();
                    packageTypes.add("extra small");
                    packageTypes.add("small");
                    packageTypes.add("medium");
                    packageTypes.add("large");
                    packageTypes.add("extra large");
                    packageTypes.add("post card");
                    packageTypes.add("envelope");
                    System.out.println("Package type (Extra Small, Small, Medium, Large, Extra Large, Post Card, Envelope)");
                    packageType = kboard.nextLine().toLowerCase(); // maintain consistency
                    if (!packageTypes.contains(packageType)){
                        System.out.println("Package type not recognized, defaulting to Extra Large");
                        packageType = "extra large";
                    }

                    ArrayList<String> deliveryTypes = new ArrayList<>();
                    deliveryTypes.add("1-day");
                    deliveryTypes.add("overnight");
                    deliveryTypes.add("Overnight");
                    deliveryTypes.add("OVERNIGHT");
                    deliveryTypes.add("3-5 day");
                    deliveryTypes.add("7+ day");
                    System.out.println("Delivery Type (1-day, overnight, 3-5 day, 7+ day)");
                    deliveryType = kboard.nextLine();
                    if (!deliveryTypes.contains(deliveryType)){
                        System.out.println("Delivery Type not recognized, defaulting to 7+ day");
                        deliveryType = "7+ day";
                    }

                    ArrayList <String> extraInformationLst = new ArrayList<>();
                    extraInformationLst.add("fragile");
                    extraInformationLst.add("hazardous");
                    System.out.println("Is there any extra information that we should know (fragile, hazardous)? If not, enter to continue");
                    extraInfo = kboard.nextLine().toLowerCase();
                    if (extraInfo.equals('\n')){
                        extraInfo = "n/a";
                    }
                    else if (!extraInformationLst.contains(extraInfo)){
                        System.out.println("Extra Information not recognized, please enter valid extra info input (fragile, hazardous): ");
                        while (!extraInformationLst.contains(extraInfo) && !extraInfo.equals('\n')){ // loops until valid input, or new line
                            System.out.println("Extra Information not recognized, please enter valid extra info input (fragile, hazardous): ");
                            extraInfo = kboard.nextLine().toLowerCase();
                        }
                        if (extraInfo.equals('\n')){
                            extraInfo = "n/a";
                        }
                    }

                    System.out.println("Enter 'city state' where you are shipping from: ");
                    locate = kboard.nextLine().toLowerCase();

                    Date wrongDate = new Date();
                    startedDelivery = wrongDate.toString();

                    break;
                case 2:
                    System.out.println("Enter Recipient Info:");
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

                    System.out.print("Country: ");
                    country = kboard.nextLine();

                    System.out.print("Zip Code: ");
                    zip = kboard.nextLine();

                    System.out.println("Recipient Info Entered");
                    stepNum = nextStep(stepNum);
                    break;
                case 3:
                    System.out.print("Choose Payment Option (Contract, Credit, Prepaid): ");
                    paymentType = kboard.nextLine();

                    if (paymentType.equalsIgnoreCase("Credit")) {
                        System.out.print("Enter Card Holder Name: ");
                        cardholderName = kboard.nextLine();

                        System.out.print("Card Number: ");
                        cardNumber = inputNumber();

                        System.out.print("Expiration Month Number: ");
                        expMonth = inputNumber();

                        System.out.print("Expiration Year Number: ");
                        expYear = inputNumber();

                        System.out.print("CVV: ");
                        cvv = inputNumber();
                    }

                    System.out.println("Payment Info Entered");
                    System.out.println("WARNING: Continuing will complete the add package process.");
                    stepNum = nextStep(stepNum);
            }
        }

        // TODO : PackageTable.addPackage(); -- DONE?

        Random rand = new Random();

        packID = rand.nextInt(49999) + 50000; // needs to be a random num not in the DB, will act as the password - 5 digits
        transactionID = rand.nextInt(49999) + 50000;

        String query = String.format("SELECT * FROM packages WHERE packageID = %d;", packID);

        String transactionQuery = String.format("SELECT * FROM packages WHERE transactionID = %d", transactionID);

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

            Statement stmt =  conn.createStatement();

            ResultSet rs = stmt.executeQuery(query); // packageID query

            while (rs.next()){ // checks if the table is empty, if not enters here
                packID = rand.nextInt(49999) + 50000;
                query = String.format("SELECT * FROM packages WHERE packageID = %d;", packID);
                rs = stmt.executeQuery(query);
            }


            ResultSet rs2 = stmt.executeQuery(transactionQuery); // transactionIF query
            while (rs2.next()){ // checks if the table is empty, if not enters here
                transactionID = rand.nextInt(49999) + 50000;
                transactionQuery = String.format("SELECT * FROM packages WHERE transactionID = %d;", transactionID);
                rs2 = stmt.executeQuery(transactionQuery);
            }

            // add package type
            packageTable.addPackage(conn, packageType, weight, deliveryType, packID, location, startedDelivery, extraInfo, deliveryType, transactionID);

            System.out.println("New Package Registered. Welcome!");
            System.out.println("Your package ID is: '" + packID);

        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }



        //TODO: Make Payment
        //TODO: Display Transaction Info
    }

    public void printStateInfo(UIState state) {
        switch (state) {
            case UNKNOWN_USER_HOME:
                System.out.println("Welcome to Four Squared!");
                break;
            case TRANSACTION_LIST:
                System.out.println("Here are the IDs from your previous transactions.\n" +
                        "Enter an ID to see transaction details.");
                //TODO add query for accessing transactions here

                break;
            case PACKAGES_LIST:
                System.out.println("Here are the IDs for your packages.\n" +
                        "Enter an ID see the package details.");
                //TODO add query for accessing package lists here
        }
    }

    private void enterSqlStatements() {
        boolean enteringStatements = true;
        String statement;

        while (enteringStatements) {
            System.out.print("Enter a SQL Statement or 'QUIT' to quit: ");
            statement = kboard.nextLine();

            if (statement.equalsIgnoreCase("QUIT")) {
                enteringStatements = false;
            }
            else {
                H2Main.enterAdminStatement(statement);
            }
        }
    }
}
