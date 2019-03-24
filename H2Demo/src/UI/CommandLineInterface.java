package UI;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import SQL.*;

public class CommandLineInterface {
    private Scanner kboard;
    private UIState state;
    private User user;

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.runCLI();
    }

    public CommandLineInterface() {
        this.kboard = new Scanner(System.in);
        this.state = UIState.WORKER_HOME;
        this.user = new User();
    }

    public void runCLI() {
        String option;

        System.out.println("Welcome to Four Squared!");

        do {
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
        System.out.println("Possible Commands: ");

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
        }

        return state;
    }

    private void scanPackages() {
        int packageId;

        System.out.println("Scanning Packages");

        while (true) {
            System.out.print("Enter PackageID: ");
            packageId = inputNumber();

            //TODO: Scan package

            System.out.print("Enter 'Y' to scan another package or anything else to stop: ");
            if (!kboard.nextLine().equalsIgnoreCase("Y")) {
                break;
            }

            System.out.println("Scanning another package");
        }
    }

    private void markDelivered() {
        int packageId;

        System.out.println("Marking Packages Deliverd");
        while (true) {
            System.out.print("Enter PackageID: ");
            packageId = inputNumber();

            //TODO: Scan package

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
                // options.addAll(getPackages()); //TODO: Add get package IDs method
                options.add("HOME");
                break;
            case TRANSACTION_LIST:
                // options.addAll(getTransactions()); //TODO: Add get transaction IDs method
                options.add("HOME");
        }

        return options;
    }

    private void displayPackageInfo(int packageId) {
        //TODO
    }

    private void displayTransactionInfo(int transactionId) {
        //TODO
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

        System.out.println("Weight: " + weight + " lbs.");
        System.out.println("First Name: " + firstName);
        System.out.println("Address: " + streetNum + " " + streetName + ", " + city + ", " + state + " " + zip + " " + country);
        //TODO: Make Package
        //TODO: Make Payment
        //TODO: Display Transaction Info
    }
}
