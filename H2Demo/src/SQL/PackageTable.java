package SQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to make and manipulate the package table
 * @author scj
 *
 */
public class PackageTable {

    /**
     * Reads a cvs file for data and adds them to the packages table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populatePackageTableFromCSV(Connection conn,
                                                    String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Package> packages = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                packages.add(new Package(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all packages
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createPackageInsertSQL(packages);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        //System.out.print(sql);
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    /**
     * Create the packages table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createPackageTable(Connection conn){
        try {
            //FOR THE LOVE OF GOD UNDO THIS
            String q = "DROP TABLE IF EXISTS packages";
            Statement stmtt = conn.createStatement();
            stmtt.execute(q);
            String query = "CREATE TABLE IF NOT EXISTS packages(packageType VARCHAR(255),weight FLOAT(2),deliveryType VARCHAR(255)," +
                    "packageID INT PRIMARY KEY,location VARCHAR(255),startedDelivery VARCHAR(255),extraInfo VARCHAR(255)," +
                    "deliveryTime VARCHAR(255),transactionID INT)";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a single package to the database
     *
     */
    public static void addPackage(Connection conn, String packageType, double weight, String deliveryType, int packageID, String location,
                                   String startedDelivery, String extraInfo, String deliveryTime, int transactionID){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO packages "
                        + "VALUES(\'%s\', %f ,\'%s\', %d, \'%s\',\'%s\',\'%s\',\'%s\',%d);",
                packageType, weight, deliveryType, packageID, location, startedDelivery, extraInfo, deliveryTime, transactionID );
        try {
            /**
             * create and execute the query
             */
            //System.out.println(query);
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This creates an sql statement to do a bulk add of packages
     *
     * @param packages: list of package objects to add
     *
     * @return
     */
    public static String createPackageInsertSQL(ArrayList<Package> packages){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO packages (packageType,weight,deliveryType,packageID,location,startedDelivery,extraInfo," +
                "deliveryTime,transactionID) VALUES");

        /**
         * For each package append a tuple
         *
         * If it is not the last package add a comma to seperate
         *
         * If it is the last package add a semi-colon to end the statement
         */
        for(int i = 0; i < packages.size(); i++){
            Package p = packages.get(i);
            sb.append(String.format("(\'%s\', %f ,\'%s\', %d, \'%s\',\'%s\',\'%s\',\'%s\',%d)",
                    p.getPackageType(), p.getWeight(), p.getDeliveryType(), p.getPackageID(), p.getLocation(), p.getStartedDelivery(),
                    p.getExtraInfo(), p.getDeliveryTime(), p.getTransactionID()));
            if( i != packages.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the package table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryPackageTable(Connection conn,
                                               ArrayList<String> columns,
                                               ArrayList<String> whereClauses){
        StringBuilder sb = new StringBuilder();

        /**
         * Start the select query
         */
        sb.append("SELECT ");

        /**
         * If we gave no columns just give them all to us
         *
         * other wise add the columns to the query
         * adding a comma top seperate
         */
        if(columns.isEmpty()){
            sb.append("* ");
        }
        else{
            for(int i = 0; i < columns.size(); i++){
                if(i != columns.size() - 1){
                    sb.append(columns.get(i) + ", ");
                }
                else{
                    sb.append(columns.get(i) + " ");
                }
            }
        }

        /**
         * Tells it which table to get the data from
         */
        sb.append("FROM packages ");

        /**
         * If we gave it conditions append them
         * place an AND between them
         */
        if(!whereClauses.isEmpty()){
            sb.append("WHERE ");
            for(int i = 0; i < whereClauses.size(); i++){
                if(i != whereClauses.size() -1){
                    sb.append(whereClauses.get(i) + " AND ");
                }
                else{
                    sb.append(whereClauses.get(i));
                }
            }
        }

        /**
         * close with semi-colon
         */
        sb.append(";");

        //Print it out to verify it made it right
        System.out.println("Query: " + sb.toString());
        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries and print the table
     * @param conn
     */
    public static void printPackageTable(Connection conn){
        String query = "SELECT * FROM packages;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("packages %s, %f ,%s, %d, %s, %s, %s, %s,%d\n",
                        result.getString(1),
                        result.getFloat(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8),
                        result.getInt(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Package getPackageInfo(int packageId, Connection conn){
        Package pack = null;

        String query = "SELECT * FROM packages WHERE packageID =" + packageId;

        try{
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);

            while(results.next()){
                pack =  new Package(results.getString(1),
                        results.getFloat(2),
                        results.getString(3),
                        results.getInt(4),
                        results.getString(5),
                        results.getString(6),
                        results.getString(7),
                        results.getString(8),
                        results.getInt(9));
            }
        }catch(Exception e){
            System.err.println(e.toString());
        }
        return pack;
    }

    public static ArrayList<Integer> getCustomerPackages(int customerId, Connection conn) {
        ArrayList<Integer> packageIds = new ArrayList<>();

        ArrayList<String> columns = new ArrayList<>();
        String query = "SELECT packageId FROM packages WHERE transactionId IN (SELECT transactionId FROM " +
                "makesTransaction WHERE customerId = " + customerId + ")";

        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);

            while (results.next()) {
                packageIds.add(results.getInt(1));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return packageIds;
    }

    public static void setPackageDelivered(int packageID, int driverId, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String statement;

            if (PackageTransportationTable.checkOutForTransport(packageID, conn)) {
                statement = "SELECT * FROM PACKAGETRANSPORTATION WHERE packageId = " + packageID + " AND " +
                        "driverId = " + driverId;
                ResultSet result = stmt.executeQuery(statement);

                if (result.next()) {
                    ZonedDateTime now = LocalDate.now(ZoneId.of("America/Montreal"))
                            .atStartOfDay(ZoneId.of("America/Montreal"));

                    statement = "UPDATE packages SET deliveryTime = '" + now + "' WHERE packageID = " + packageID;
                    stmt.execute(statement);

                    System.out.println("Package was delivered at: " + now);

                    PackageTransportationTable.removeFromTransport(packageID, conn);
                }
                else {
                    System.out.println("This package is not in your transport inventory.");
                }
            } else {
                statement = "SELECT * FROM packages WHERE packageId = " + packageID;
                ResultSet result = stmt.executeQuery(statement);
                if (!result.next()) {
                    System.out.println("Package with ID: " + packageID + " does not exist.");
                }
                else {
                    System.out.println("Package with ID: " + packageID + " is not out for delivery.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void scanPackageIn(int packageID, int workerID, Connection conn) {
        //Get worker location
        String loc = "";
        String statement = "SELECT location FROM workers WHERE workerID = " + workerID;

        try {
            Statement stmt = conn.createStatement();

            if (PackageTransportationTable.checkOutForTransport(packageID, conn)) {
                ResultSet result = stmt.executeQuery(statement);
                if (result.next()) {
                    loc = result.getString(1);
                }

                statement = "UPDATE packages SET location = '" + loc +
                        "' WHERE packageID = " + packageID;
                stmt.execute(statement);
                System.out.println("Package with ID: " + packageID + " checked in at " + loc);

                PackageTransportationTable.removeFromTransport(packageID, conn);
            }
            else {
                statement = "SELECT location, deliveryTime FROM packages WHERE packageId = " + packageID;
                ResultSet result = stmt.executeQuery(statement);
                if (result.next()) {
                    String lcoation = result.getString(1);
                    String deliveryTime = result.getString(2);

                    if (result.getString(2).equals("null")) {
                        System.out.println("Package is not currently in transport. It is located at: " + lcoation);
                    }
                    else {
                        System.out.println("Package has already been delivered. Package delivered at: " + deliveryTime);
                    }
                } else {
                    System.out.println("Package with ID: " + packageID + " does not exist.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scanPackageOut(int packageId, int workerId, Connection conn) {
        int workerNum = PostalWorkerTable.getWorkerNum(workerId, conn);
        int transportId = TransportationTable.pickTransport(workerNum, conn);

        try {
            String statement;
            Statement stmt = conn.createStatement();

            if (!PackageTransportationTable.checkOutForTransport(packageId, conn)) {
                statement = "SELECT location, deliveryTime FROM PACKAGES WHERE packageId = " + packageId;
                ResultSet result = stmt.executeQuery(statement);

                if (result.next()) {
                    String location = result.getString(1);
                    String deliveryTime = result.getString(2);

                    if (!deliveryTime.equals("null")) {
                        System.out.println("Package: " + packageId + " hsa already been delivered. " +
                                "It was delivered on " + deliveryTime);
                    }
                    else {
                        statement = "SELECT location FROM WORKERS WHERE workerId = " + workerId;
                        result = stmt.executeQuery(statement);
                        if (result.next()) {
                            String workerLocation = result.getString(1);

                            if (workerLocation.equals(location)) {
                                PackageTransportationTable.addPackageTransportation(conn, packageId, transportId);
                                System.out.println("Package: " + packageId + " is now out for delivery on Transport: "
                                        + transportId);
                            } else {
                                System.out.println("Package: " + packageId + " is not located at: " + workerLocation +
                                        ". It is located at " + location);
                            }
                        }
                    }
                }
                else {
                    System.out.println("Package with ID: " + packageId + " does not exist.");
                }
            }
            else {
                statement = "SELECT transportId FROM PACKAGETRANSPORTATION WHERE packageId = " + packageId;
                ResultSet result = stmt.executeQuery(statement);

                if (result.next()) {
                    System.out.println("Package: " + packageId + " is already on Transport: " + transportId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}