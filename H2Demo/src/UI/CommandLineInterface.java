package UI;

import java.util.Scanner;
import java.util.ArrayList;

public class CommandLineInterface {
    private Scanner kboard;
    private UIState state;
    private User user;

    public CommandLineInterface() {
        this.kboard = new Scanner(System.in);
        this.state = UIState.UNKNOWN_USER_HOME;
        this.user = new User();
    }

    public void runCLI() {
        String option;

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
                        String packageId = kboard.nextLine();
                        getPackageInfo(packageId);
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
                        break;
                    case "BILLING":
                        return UIState.TRANSACTION_LIST;
                }
                break;
            case WORKER_HOME:
                switch (option.toUpperCase()) {
                    case "LOGOUT":
                        user.logout();
                        break;
                    case "SCAN":
                        break;
                    case "DELIVER":

                }
                break;
            case PACKAGES_LIST:
                if (option.equalsIgnoreCase("HOME")) {
                    return UIState.CUSTOMER_HOME;
                }
                else {
                    // displayPackageInfo(option);
                    // TODO: Display package info
                    return state;
                }
            case TRANSACTION_LIST:
                if (option.equalsIgnoreCase("HOME")) {
                    return UIState.CUSTOMER_HOME;
                }
                else {
                    // displayTransactionInfo(option);
                    //TODO: Display transaction info
                    return state;
                }
        }

        return state;
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
                // options.addAll(getPackages(user)); //TODO: Add get package IDs method
                options.add("HOME");
                break;
            case TRANSACTION_LIST:
                // options.addAll(getTransactions()); //TODO: Add get transaction IDs method
                options.add("HOME");
        }

        return options;
    }

    private void getPackageInfo(String packageId) {
        return false;
    }
}

