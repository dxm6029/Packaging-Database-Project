package UI;

import java.sql.*;
import java.util.*;
import java.util.Date;

import SQL.*;
import SQL.Package;

public class CommandLineInterface {
    private Scanner kboard;
    private UIState state;
    private User user;
    private H2Main demo = new H2Main();
    private String location = "./h2demo/h2demo";
    private String use = "me";
    private String password = "password";
    private Connection connect;



    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.runCLI();
    }

    public CommandLineInterface() {
        this.kboard = new Scanner(System.in);
        this.state = UIState.UNKNOWN_USER_HOME;
        this.user = new User();
        demo.createConnection(location, use, password);
        connect = demo.getConnection();
    }

    public Connection getConnection() {
        return this.connect;
    }

    /**
     * Runs the UI. This method gets the current options based on the state,
     * then prints them and asks for input. If input valid, it executes the option.
     */
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

    /**
     * Prints the possible commands you can perform for the current state
     */
    private void printOptions(ArrayList<String> options) {
        System.out.println("Possible Commands: (Or type 'QUIT' to exit)");

        for  (String option : options) {
            System.out.println("\t\t" + option);
        }
    }

    /**
     * Executes the command associated with the selected option.
     */
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
                    case "SCANIN":
                        scanPackagesIn();
                        return state;
                    case "SCANOUT":
                        scanPackagesOut();
                        return state;
                }
                break;
            case PACKAGES_LIST:
                if (option.equalsIgnoreCase("HOME")) {
                    return UIState.CUSTOMER_HOME;
                }
                else if(option.equalsIgnoreCase("ALL")) {
                    displayAllPackageInfo();
                    return state;
                }
                else if(option.equalsIgnoreCase("LIST CONTRACT PACKAGES")){
                    listContractPackages();
                    return state;
                }
                else {
                    displayPackageInfo(Integer.parseInt(option));
                    return state;
                }
            case TRANSACTION_LIST:
                if (option.equalsIgnoreCase("HOME")) {
                    return UIState.CUSTOMER_HOME;
                }
                else if(option.equalsIgnoreCase("ALL")) {
                    displayAllTransactionInfo();
                    return state;
                }
                else if(option.equalsIgnoreCase("PAY CONTRACT")){
                    getPayment();
                    return state;
                }
                else if(option.equalsIgnoreCase("LIST CONTRACT PACKAGES")){
                    listContractTransactions();
                    return state;
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
            case DRIVER_HOME:
                if (option.equalsIgnoreCase("DELIVER")) {
                    markDelivered();
                    return state;
                }
                else {
                    return user.logout();
                }
        }

        return state;
    }
    private void listContractPackages() {
        int customerID = Integer.parseInt(user.getUserId());
        try {
            Statement stmt = connect.createStatement();
            String getPayID = String.format("(SELECT paymentID FROM contract) INTERSECT (SELECT paymentID FROM makesTransaction WHERE customerID = %d)", customerID);
            ResultSet r = stmt.executeQuery(getPayID);
            r.next();
            int paymentID = r.getInt(1);
            String getpackID = String.format("SELECT packageID FROM packages WHERE transactionID IN (SELECT transactionID FROM makesTransaction WHERE paymentID = %d)", paymentID);
            r = stmt.executeQuery(getpackID);
            while(r.next()) {
                System.out.println();
                displayPackageInfo(r.getInt(1));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void listContractTransactions() {
        int customerID = Integer.parseInt(user.getUserId());
        try {
            Statement stmt = connect.createStatement();
            String getPayID = String.format("(SELECT paymentID FROM contract) INTERSECT (SELECT paymentID FROM makesTransaction WHERE customerID = %d)", customerID);
            ResultSet r = stmt.executeQuery(getPayID);
            r.next();
            int paymentID = r.getInt(1);
            String getTransID = String.format("SELECT transactionID FROM makesTransaction WHERE paymentID = %d", paymentID);
            r = stmt.executeQuery(getTransID);
            while(r.next()) {
                System.out.println();
                displayTransactionInfo(r.getInt(1));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void getPayment() {
        boolean multiTry = false;
        int expYear = 0;
        int expMonth = 0;
        String cardholderName;
        String cardNumber;
        String cvv;
        Double paymentAmount;
        double total = 0;

        int customerID = Integer.parseInt(user.getUserId());

        try {
            Statement stmt = connect.createStatement();
            String getPayID = String.format("(SELECT paymentID FROM contract) INTERSECT (SELECT paymentID FROM makesTransaction WHERE customerID = %d)", customerID);
            ResultSet r = stmt.executeQuery(getPayID);
            r.next();
            int paymentID = r.getInt(1);
            String getPaidAmount = String.format("SELECT paid FROM contract WHERE paymentID = %d", paymentID);
            r = stmt.executeQuery(getPaidAmount);
            r.next();
            double paid = r.getDouble(1);
            String getTotal = String.format("SELECT packageType, weight, deliveryType FROM packages WHERE transactionID IN (SELECT transactionID FROM makesTransaction WHERE paymentID = %d)", paymentID);
            r = stmt.executeQuery(getTotal);

            // go through resulting table adding all the prices
            while (r.next()) {
                total += getPrice(r.getString(1), r.getDouble(2), r.getString(3));
            }
            //System.out.println(total + " " + paid);
            total -= paid;
            total = (double) Math.round(total * 100) / 100;
        } catch (SQLException e) {

        }
        if (total <= 0) {
            //System.out.println("Yes");
            System.out.println("No Payment Needed");
            return;
        } else {
            System.out.println("Total Account Balance: " + total);
        }

        while ((expYear < Calendar.getInstance().get(Calendar.YEAR)) ||
                (expYear == Calendar.getInstance().get(Calendar.YEAR) && expMonth <= Calendar.getInstance().get(Calendar.MONTH))) {
            if (multiTry) {
                System.out.println("ENTER VALID CREDIT CARD PLEASE!");
            }
            System.out.print("Enter Card Holder Name: ");
            cardholderName = kboard.nextLine();
            while (cardholderName.length() == 0) {
                System.out.print("Invalid Input. Please enter a name: ");
                cardholderName = kboard.nextLine();
            }

            System.out.print("Card Number(XXXX-XXXX-XXXX-XXXX): ");
            cardNumber = kboard.nextLine();
            while (!cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
                System.out.print("Invalid input. Please enter in format XXXX-XXXX-XXXX-XXXX: ");
                cardNumber = kboard.nextLine();
            }

            System.out.print("Expiration Month Number: ");
            expMonth = kboard.nextInt();
            while (expMonth > 12 || expMonth < 1) {
                System.out.print("Invalid input. Please enter in range 1-12: ");
                expMonth = kboard.nextInt();
            }

            System.out.print("Expiration Year Number: ");
            expYear = kboard.nextInt();
            while (expYear < 0) {
                System.out.print("Invalid input. Please enter valid year: ");
                expYear = kboard.nextInt();
            }
            kboard.nextLine();
            System.out.print("CVV: ");
            cvv = kboard.nextLine();
            while (cvv.length() != 3) {
                System.out.print("Invalid input. Please enter valid CVV(XXX): ");
                cvv = kboard.nextLine();
            }
            multiTry = true;
        }
        System.out.print("Amount to be Paid: ");
        paymentAmount = kboard.nextDouble();

        while (paymentAmount > total) {
            System.out.print("Invalid Amount. New amount to be Paid: ");
            paymentAmount = kboard.nextDouble();
        }

        String amountPaid = String.format("UPDATE contract SET paid = paid + %f", paymentAmount);
        try {
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(amountPaid);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        kboard.nextLine();
        //show new balance
        total = 0;
        try {
            Statement stmt = connect.createStatement();
            String getPayID = String.format("(SELECT paymentID FROM contract) INTERSECT (SELECT paymentID FROM makesTransaction WHERE customerID = %d)", customerID);
            ResultSet r = stmt.executeQuery(getPayID);
            r.next();
            int paymentID = r.getInt(1);
            String getPaidAmount = String.format("SELECT paid FROM contract WHERE paymentID = %d", paymentID);
            r = stmt.executeQuery(getPaidAmount);
            r.next();
            double paid = r.getDouble(1);
            String getTotal = String.format("SELECT packageType, weight, deliveryType FROM packages WHERE transactionID IN (SELECT transactionID FROM makesTransaction WHERE paymentID = %d)", paymentID);
            r = stmt.executeQuery(getTotal);

            // go through resulting table adding all the prices
            while (r.next()) {
                total += getPrice(r.getString(1), r.getDouble(2), r.getString(3));
            }
            total -= paid;
            total = (double) Math.round(total * 100) / 100;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("OH NO");
        }
        if (total <= 0) {
            System.out.println("No More Payment Needed");
        } else {
            System.out.println("New Total Account Balance: " + total);
        }

    }

    private void displayAllTransactionInfo() {
        ArrayList<Integer> transactions = H2Main.getTransactionIds(Integer.parseInt(user.getUserId()));
        for (int id : transactions) {
            System.out.println();
            displayTransactionInfo(id);
            System.out.println();
        }
    }

    private void displayAllPackageInfo() {
        ArrayList<Integer> packages = H2Main.getPackageIds(Integer.parseInt(user.getUserId()));
        for(int p : packages){
            System.out.println();
            displayPackageInfo(p);
            System.out.println();
        }
    }


    /**
     * Runs a loop of scanning packages until the user exits.
     */
    private void scanPackagesIn() {
        int packageId;

        System.out.println("Scanning Packages In");

        while (true) {
            System.out.print("Enter PackageID (Or -1 to cancel): ");
            packageId = inputNumber();
            if (packageId == -1) {
                break;
            }

            H2Main.scanPackageIn(packageId, Integer.parseInt(user.getUserId()));

            System.out.print("Enter 'Y' to scan another package or anything else to stop: ");
            if (!kboard.nextLine().equalsIgnoreCase("Y")) {
                break;
            }

            System.out.println("Scanning another package");
        }
    }

    private void scanPackagesOut() {
        int packageId;

        System.out.println("Scanning Packages Out");

        while (true) {
            System.out.print("Enter PackageID (Or -1 to cancel): ");
            packageId = inputNumber();
            if (packageId == -1) {
                break;
            }

            PackageTable.scanPackageOut(packageId, Integer.parseInt(user.getUserId()), connect);

            System.out.print("Enter 'Y' to scan another package or anything else to stop: ");
            if (!kboard.nextLine().equalsIgnoreCase("Y")) {
                break;
            }

            System.out.println("Scanning another package");
        }
    }


    /**
     * Runs a loop of delivering packages until user exits.
     */
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

    /**
     * Gets the valid options for the current state.
     */
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
                options.add("SCANIN");
                options.add("SCANOUT");
                options.add("LOGOUT");
                break;
            case PACKAGES_LIST:
                ArrayList<Integer> packages = H2Main.getPackageIds(Integer.parseInt(user.getUserId()));
                for (int id: packages) {
                    options.add(Integer.toString(id));
                }
                options.add("ALL");
                if(hasContract()) {
                    options.add("LIST CONTRACT PACKAGES");
                }
                options.add("HOME");
                break;
            case TRANSACTION_LIST:
                ArrayList<Integer> transactions = H2Main.getTransactionIds(Integer.parseInt(user.getUserId()));
                for (int id : transactions) {
                    options.add(Integer.toString(id));
                }
                options.add("ALL");
                if(hasContract()){
                    options.add("LIST CONTRACT PACKAGES");
                    options.add("PAY CONTRACT");
                }
                options.add("HOME");
                break;
            case ADMIN:
                options.add("SQL");
                options.add("LOGOUT");
                break;
            case DRIVER_HOME:
                options.add("DELIVER");
                options.add("LOGOUT");
        }

        return options;
    }

    private boolean hasContract(){
        int customerID = Integer.parseInt(user.getUserId());
        String getPaymID = String.format("SELECT * FROM contract WHERE paymentID IN (SELECT paymentID FROM makesTransaction WHERE customerID = %d)", customerID);
        try {
            Statement stmt = connect.createStatement();
            ResultSet r = stmt.executeQuery(getPaymID);
            if(r.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Displayes info for the selected package
     */
    private void displayPackageInfo(int packageId) {
        Package p = H2Main.getPackageInfo(packageId);

        if (p == null) {
            System.out.println("Invalid Package ID");
        }
        else {
            int transportId = PackageTransportationTable.getTransportId(packageId, connect);

            String[] packageInfo = p.toString().split("%");
            String trackingHistory = PackageLocationTable.getTrackingHistory(packageId, connect);
            String info = String.join("\n", packageInfo[0], trackingHistory, packageInfo[1]);
            System.out.println(info);
            if (transportId == -1) {
                if (p.getDeliveryTime().equals("null")) {
                    System.out.println("This package is in a warehouse at it's last check in location.");
                }
                else {
                    System.out.println("This package has been delivered.");
                }
            }
            else {
                String company = TransportationTable.getTransportCompany(transportId, connect);
                System.out.println("This package is currently in transit on transport: " + transportId +
                        " from company " + company);
            }
        }

        System.out.print("\nPress Enter to continue.");
        kboard.nextLine();
    }

    /**
     * Displays info for the selected transaction
     */
    private void displayTransactionInfo(int transactionId) {
        Transaction t = H2Main.getTransactionInfo(transactionId);
        String paymentType = H2Main.getTransactionPaymentType(transactionId);

        paymentType = paymentType.toLowerCase();
        System.out.println("\n" + t.toString());
        //System.out.println("Payment Type Used: " + paymentType + "\n");

        switch (paymentType){
            case "prepaid":
                String getPaymID = String.format("SELECT paymentID FROM makesTransaction WHERE transactionID = %d", transactionId);
                try {
                    Statement stmt = connect.createStatement();
                    ResultSet r = stmt.executeQuery(getPaymID);
                    r.next();
                    int paymentID = r.getInt(1);
                    System.out.println("PaymentID: " + paymentID + '\n');
                    String getPack = String.format("SELECT packageType, weight, deliveryType FROM packages WHERE transactionID = %d", transactionId);
                    r = stmt.executeQuery(getPack);
                    r.next();
                    double price = getPrice(r.getString(1), r.getDouble(2), r.getString(3));
                    price = (double) Math.round(price * 100)/100;
                    System.out.println("The prepaid price is: " + price);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("Prepaid applied");
                break;
            case "credit card":
                String getPayID = String.format("SELECT paymentID FROM makesTransaction WHERE transactionID = %d", transactionId);
                try {
                    Statement stmt = connect.createStatement();
                    ResultSet r = stmt.executeQuery(getPayID);
                    r.next();
                    int paymentID = r.getInt(1);
                    String select = String.format("SELECT * FROM credit WHERE paymentID = %d", paymentID);
                    r = stmt.executeQuery(select);
                    r.next();
                    System.out.println("PaymentID: " + paymentID);
                    System.out.println("Card Holder Name: " + r.getString(2));
                    System.out.println("Card Number: " + r.getString(3));
                    System.out.println("CVV: " + r.getString(4));
                    System.out.println("Expiration Date: " + r.getString(5) + '\n');
                    String getPack = String.format("SELECT packageType, weight, deliveryType FROM packages WHERE transactionID = %d", transactionId);
                    r = stmt.executeQuery(getPack);
                    r.next();
                    double price = getPrice(r.getString(1), r.getDouble(2), r.getString(3));
                    price = (double) Math.round(price * 100)/100;
                    System.out.println("The credit amount owed is: $" + price);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "contract":
                String getPaymentID = String.format("SELECT paymentID FROM makesTransaction WHERE transactionID = %d", transactionId);
                try {
                    Statement stmt = connect.createStatement();
                    ResultSet r = stmt.executeQuery(getPaymentID);
                    r.next();
                    int paymentID = r.getInt(1);
                    String select = String.format("SELECT * FROM contract WHERE paymentID = %d", paymentID);
                    r = stmt.executeQuery(select);
                    r.next();
                    System.out.println("PaymentID: " + paymentID);
                    System.out.println("Bill Date: " + r.getString(2));
                    System.out.println("Total Number of Packages: " + r.getInt(3));

                    String getPack = String.format("SELECT packageType, weight, deliveryType FROM packages WHERE transactionID = %d", transactionId);
                    r = stmt.executeQuery(getPack);
                    r.next();
                    double price = getPrice(r.getString(1), r.getDouble(2), r.getString(3));
                    price = (double) Math.round(price * 100)/100;
                    System.out.println("The price for this package is: $" + price);

                    String getTotal = String.format("SELECT packageType, weight, deliveryType FROM packages WHERE transactionID IN (SELECT transactionID FROM makesTransaction WHERE paymentID = %d)", paymentID);
                    r = stmt.executeQuery(getTotal);

                    // go through resulting table adding all the prices
                    double total = 0;
                    while (r.next()){
                        total += getPrice(r.getString(1), r.getDouble(2), r.getString(3));
                    }

                    String getPaid = String.format("SELECT paid FROM contract WHERE paymentID = %d", paymentID);
                    ResultSet resultSet = stmt.executeQuery(getPaid);
                    resultSet.next();

                    double sub = resultSet.getDouble(1);
                    total = (double) Math.round(total * 100)/100;
                    System.out.println("Total Accumulated Costs of Contract Packages: $" + total);
                    total -= sub;

                    total = (double) Math.round(total * 100)/100;

                    System.out.println("Current Contract Balance: $" + total);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }


        System.out.print("Press Enter to continue.");
        kboard.nextLine();
    }

    /**
     * Handles the input of a number, because it's different from a string input
     */
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

    /**
     * Determines which step to go to based on the current step and user input.
     */
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

    /**
     * Asks for user input in order to add a package
     */
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
        String cardNumber = "";
        int expMonth = -1;
        int expYear = -1;
        String cvv = "";
        int payID = 0;


        while (stepNum < 4) {
            switch (stepNum) {
                case 0:
                    return;
                case 1:
                    System.out.print("Enter Package Weight: ");
                    while (weight <= 0) {
                        try {
                            weight = kboard.nextDouble();
                            if(weight > 0){
                                break;
                            }
                            System.out.print("Invalid Input. Please enter a positive number: ");
                            kboard.nextLine();
                        }
                        catch (InputMismatchException e) {
                            System.out.print("Invalid Input. Please enter a positive number: ");
                            kboard.nextLine();
                        }
                    }
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
                    System.out.print("Package type (Extra Small, Small, Medium, Large, Extra Large, Post Card, Envelope): ");
                    packageType = kboard.nextLine().toLowerCase(); // maintain consistency
                    while (!packageTypes.contains(packageType)){
                        System.out.print("Package type not recognized, please try again (Extra Small, Small, Medium, Large, Extra Large, Post Card, Envelope): ");
                        packageType = kboard.nextLine().toLowerCase();
                    }

                    ArrayList<String> deliveryTypes = new ArrayList<>();
                    deliveryTypes.add("1-day");
                    deliveryTypes.add("overnight");
                    deliveryTypes.add("3-5 day");
                    deliveryTypes.add("7+ day");
                    System.out.print("Delivery Type (1-day, overnight, 3-5 day, 7+ day): ");
                    deliveryType = kboard.nextLine().toLowerCase();
                    while (!deliveryTypes.contains(deliveryType)){
                        System.out.print("Delivery Type not recognized, please try again (1-day, overnight, 3-5 day, 7+ day)");
                        deliveryType = kboard.nextLine().toLowerCase();
                    }

                    double price = getPrice(packageType, weight, deliveryType);
                    System.out.println("Price for package: " + price);

                    ArrayList <String> extraInformationLst = new ArrayList<>();
                    extraInformationLst.add("fragile");
                    extraInformationLst.add("hazardous");
                    System.out.print("Is there any extra information that we should know (fragile, hazardous)? If not, enter to continue: ");
                    extraInfo = kboard.nextLine().toLowerCase();
                    if (extraInfo.equals("")){
                        extraInfo = "n/a";
                    }
                    else if (!extraInformationLst.contains(extraInfo)){
                        System.out.print("Extra Information not recognized, please enter valid extra info input (fragile, hazardous): ");
                        while (!extraInformationLst.contains(extraInfo) && !extraInfo.equals("")){ // loops until valid input, or new line
                            System.out.print("Extra Information not recognized, please enter valid extra info input (fragile, hazardous): ");
                            extraInfo = kboard.nextLine().toLowerCase();
                        }
                        if (extraInfo.equals('\n')){
                            extraInfo = "n/a";
                        }
                    }

                    System.out.print("Enter 'city; state' where you are shipping from: ");
                    locate = kboard.nextLine();
                    while (locate.equals("") || locate.contains(",")){
                        if(locate.contains(",")){
                            System.out.print("Please remove comma :)");
                        }
                        else {
                            System.out.print("Invalid Input. Enter 'city; state' where you are shipping from: ");
                        }
                        locate = kboard.nextLine();
                    }

                    Date date = new Date();
                    startedDelivery = date.toString();

                    break;
                case 2:
                    System.out.println("Enter Recipient Info:");
                    System.out.print("First Name: ");
                    firstName = kboard.nextLine();
                    while(firstName.equals("")){
                        System.out.print("Invalid Input. First Name: ");
                        firstName = kboard.nextLine();
                    }

                    System.out.print("Last Name: ");
                    lastName = kboard.nextLine();
                    while(lastName.equals("")){
                        System.out.print("Invalid Input. Last Name: ");
                        lastName = kboard.nextLine();
                    }

                    System.out.print("Street Number: ");
                    streetNum = kboard.nextLine();
                    while(streetNum.equals("")){
                        System.out.print("Invalid Input. Street Number: ");
                        streetNum = kboard.nextLine();
                    }

                    System.out.print("Street Name: ");
                    streetName = kboard.nextLine();
                    while(streetName.equals("")){
                        System.out.print("Invalid Input. Street Name: ");
                        streetName = kboard.nextLine();
                    }

                    System.out.print("Apt. Number: ");
                    aptNum = kboard.nextLine();
                    while(aptNum.equals("")){
                        System.out.print("Invalid Input. Apt. Number: ");
                        aptNum = kboard.nextLine();
                    }

                    System.out.print("City: ");
                    city = kboard.nextLine();
                    while(city.equals("")){
                        System.out.print("Invalid Input. City: ");
                        city = kboard.nextLine();
                    }

                    System.out.print("State: ");
                    state = kboard.nextLine();
                    while(state.equals("")){
                        System.out.print("Invalid Input. State: ");
                        state = kboard.nextLine();
                    }

                    System.out.print("Country: ");
                    country = kboard.nextLine();
                    while(country.equals("")){
                        System.out.print("Invalid Input. Country: ");
                        country = kboard.nextLine();
                    }

                    System.out.print("Zip Code: ");
                    zip = kboard.nextLine();
                    while(zip.equals("")){
                        System.out.print("Invalid Input. Zip Code: ");
                        zip = kboard.nextLine();
                    }

                    System.out.println("Recipient Info Entered");
                    stepNum = nextStep(stepNum);
                    break;
                case 3:
                    System.out.print("Choose Payment Option (Contract, Credit Card, Prepaid): ");
                    paymentType = kboard.nextLine();
                    while(!(paymentType.equalsIgnoreCase("Contract") ||
                        paymentType.equalsIgnoreCase("Credit Card") ||
                        paymentType.equalsIgnoreCase("Prepaid"))){
                        System.out.print("Invalid Input. Choose Payment Option (Contract, Credit Card, Prepaid): ");
                        paymentType = kboard.nextLine();
                    }

                    if (paymentType.equalsIgnoreCase("Credit Card")) {
                        boolean multiTry = false;
                        while((expYear < Calendar.getInstance().get(Calendar.YEAR)) ||
                                (expYear == Calendar.getInstance().get(Calendar.YEAR) && expMonth <= Calendar.getInstance().get(Calendar.MONTH))) {
                            if (multiTry) {
                                System.out.println("ENTER VALID CREDIT CARD PLEASE!");
                            }
                            System.out.print("Enter Card Holder Name: ");
                            cardholderName = kboard.nextLine();
                            while(cardholderName.length() == 0){
                                System.out.print("Invalid Input. Please enter a name: ");
                                cardholderName = kboard.nextLine();
                            }

                            System.out.print("Card Number(XXXX-XXXX-XXXX-XXXX): ");
                            cardNumber = kboard.nextLine();
                            while (!cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
                                System.out.print("Invalid input. Please enter in format XXXX-XXXX-XXXX-XXXX: ");
                                cardNumber = kboard.nextLine();
                            }

                            System.out.print("Expiration Month Number: ");
                            while (expMonth > 12 || expMonth < 1) {
                                try {
                                    expMonth = kboard.nextInt();
                                    if(! (expMonth > 12 || expMonth < 1)){
                                        break;
                                    }
                                    System.out.print("Invalid input. Please enter in range 1-12: ");
                                    kboard.nextLine();
                                }
                                catch (InputMismatchException e) {
                                    System.out.print("Invalid input. Please enter in range 1-12: ");
                                    kboard.nextLine();
                                }
                            }
                            kboard.nextLine();

                            System.out.print("Expiration Year Number: ");
                            while (expYear < 0) {
                                try {
                                    expYear = kboard.nextInt();
                                    kboard.nextLine();
                                    if( expYear >= 0){
                                        break;
                                    }
                                    System.out.print("Invalid input. Please enter valid year: ");
                                    kboard.nextLine();
                                }
                                catch (InputMismatchException e) {
                                    System.out.print("Invalid input. Please enter valid year: ");
                                    kboard.nextLine();
                                }
                            }

                            System.out.print("CVV: ");
                            cvv = kboard.nextLine();
                            while (cvv.length() != 3) {
                                System.out.print("Invalid input. Please enter valid CVV(XXX): ");
                                cvv = kboard.nextLine();
                            }
                            multiTry = true;
                        }
                    }
                    else if(paymentType.equalsIgnoreCase("Prepaid")){
                        System.out.println("Payment Processed");
                    }
                    else if(paymentType.equalsIgnoreCase("Contract")){
                        System.out.println("Added to Contract");
                    }
                    System.out.println("Payment Info Entered");
                    System.out.println("WARNING: Continuing will complete the add package process.");
                    stepNum = nextStep(stepNum);
            }
        }

        Random rand = new Random();

        packID = rand.nextInt(49999) + 50000; // needs to be a random num not in the DB, will act as the password - 5 digits
        transactionID = rand.nextInt(49999) + 50000;
        payID = rand.nextInt(49999) + 50000;

        String query = String.format("SELECT * FROM packages WHERE packageID = %d;", packID);

        String transactionQuery = String.format("SELECT * FROM packages WHERE transactionID = %d", transactionID);

        String payQuery = String.format("SELECT * FROM makesTransaction WHERE paymentID = %d", payID);

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


            ResultSet rs2 = stmt.executeQuery(transactionQuery); // transactionID query
            while (rs2.next()){ // checks if the table is empty, if not enters here
                transactionID = rand.nextInt(49999) + 50000;
                transactionQuery = String.format("SELECT * FROM packages WHERE transactionID = %d;", transactionID);
                rs2 = stmt.executeQuery(transactionQuery);
            }
            ResultSet rs3 = stmt.executeQuery(payQuery); // payment query
            if(paymentType.equalsIgnoreCase("Contract")){
                payQuery = String.format("SELECT * FROM contract where paymentID IN (SELECT paymentID FROM makesTransaction where customerID = %s)", this.user.getUserId());
                rs3 = stmt.executeQuery(payQuery);
                if(rs3.next()){
                    payID = rs3.getInt(1);
                    payQuery = String.format("UPDATE contract SET totalPackageNum = totalPackageNum + 1 WHERE paymentID = %d", payID);
                    stmt.executeUpdate(payQuery);
                }
            }else {
                while (rs3.next()) { // checks if the table is empty, if not enters here
                    payID = rand.nextInt(49999) + 50000;
                    payQuery = String.format("SELECT * FROM makesTransaction WHERE paymentID = %d;", payID);
                    rs3 = stmt.executeQuery(payQuery);
                }
                PaymentTable.addPayment(conn, payID, paymentType);
                if(paymentType.equalsIgnoreCase("Contract")){
                    int month = rand.nextInt(12) + 1;
                    String billDate = month + "/1";
                    ContractTable.addContract(conn, payID, billDate, 1, 0);
                }
                else if(paymentType.equalsIgnoreCase("Prepaid")){
                    PrepaidTable.addPrepaid(conn, payID, 1);
                }
                else if(paymentType.equalsIgnoreCase("Credit Card")){
                    CreditCardTable.addCredit(conn, payID, cardholderName, cardNumber, cvv, expMonth + "/" + expYear);
                }
            }


            // add package type
            PackageTable.addPackage(conn, packageType, weight, deliveryType, packID, locate, startedDelivery, extraInfo, null, transactionID);

            TransactionTable.addTransaction(conn, transactionID, firstName, lastName, streetNum, streetName, aptNum, city, state, country, zip);

            MakesTransactionTable.addMakeTransaction(conn, Integer.parseInt(this.user.getUserId()), transactionID, payID);

            int transportId = TransportationTable.pickTransport(rand.nextInt(9), conn);
            PackageTransportationTable.addPackageTransportation(conn, packID, transportId);

            System.out.println("New Package Registered. Welcome!");
            System.out.println("Your package ID is: " + packID);

        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }



        //TODO: Make Payment
        //TODO: Display Transaction Info
    }

    /**
     *  Prints info regarding the current state prior to printing
     *  the state options so it does not interfere with input.
     */
    public void printStateInfo(UIState state) {
        switch (state) {
            case UNKNOWN_USER_HOME:
                System.out.println("Welcome to Four Squared!");
                break;
            case TRANSACTION_LIST:
                System.out.println("Here are the IDs from your previous transactions.\n" +
                        "Enter an ID to see transaction details.");
                break;
            case PACKAGES_LIST:
                System.out.println("Here are the IDs for your packages.\n" +
                        "Enter an ID see the package details.");
        }
    }

    /**
     * Goes into a loop for the admin to enter SQL statements
     */
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


    public double getPrice(String packageType, double weight, String deliveryType){
        double scaleWeight = weight * 2;
        packageType = packageType.toLowerCase();
        deliveryType = deliveryType.toLowerCase();
        double price = 0;
        switch (packageType){
            case "envelope":
                price = 0.50;
                break;
            case "post card":
                price = 0.50;
                break;
            case "extra small":
                price = scaleWeight + 2;
                break;
            case "small":
                price = scaleWeight + 4;
                break;
            case "medium":
                price = scaleWeight + 6;
                break;
            case "large":
                price = scaleWeight + 8;
                break;
            default:
                price = scaleWeight + 10;
        }

        switch (deliveryType){
            case "1-day":
                price += 10;
                break;
            case "overnight":
                price += 15;
                break;
            case "3-5 day":
                price += 5;
                break;
            case "7+ day":
                price += 0;
                break;
        }

        return price;
    }
}
