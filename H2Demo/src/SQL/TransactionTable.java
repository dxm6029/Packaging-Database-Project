package SQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class to make and manipulate the transaction table
 * @author scj
 *
 */
public class TransactionTable {

    /**
     * Reads a cvs file for data and adds them to the transaction table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populateTransactionTableFromCSV(Connection conn,
                                                    String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                transactions.add(new Transaction(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all customers
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createTransactionsInsertSQL(transactions);

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
     * Create the transaction table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createTransactionsTable(Connection conn){
        try {
            //FOR THE LOVE OF GOD UNDO THIS
            String q = "DROP TABLE IF EXISTS transactions";
            Statement stmtt = conn.createStatement();
            stmtt.execute(q);
            String query = "CREATE TABLE IF NOT EXISTS transactions(transactionID INT PRIMARY KEY,rfName VARCHAR(255),"
                    + "lrName VARCHAR(225),streetNum VARCHAR(50),streetName VARCHAR(255),apptNum VARCHAR(50),city VARCHAR(225),"
                    + "state VARCHAR(225),country VARCHAR(225),zip VARCHAR(225))";

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
     * Adds a single transaction to the database
     *
     */
    public static void addTransaction(Connection conn, int transactionID, String rfName, String lrName, String streetNum,
                                   String streetName, String apptNum, String city, String state, String country, String zip){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO transactions "
                        + "VALUES(%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\');",
                transactionID, rfName, lrName, streetNum, streetName, apptNum, city, state, country, zip );
        try {
            /**
             * create and execute the query
             */
            System.out.println(query);
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This creates an sql statement to do a bulk add of transactions
     *
     * @param transactions: list of Person objects to add
     *
     * @return
     */
    public static String createTransactionsInsertSQL(ArrayList<Transaction> transactions){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO transactions (transactionID,rfName,lrName,streetNum,streetName,apptNum,city,state,country,zip) VALUES");

        /**
         * For each person append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last person add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for(int i = 0; i < transactions.size(); i++){
            Transaction t = transactions.get(i);
            sb.append(String.format("(%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",
                    t.getTransactionID(), t.getRfName(), t.getLrName(), t.getStreetNum(), t.getStreetName(), t.getApptNum(),
                    t.getCity(), t.getState(), t.getCountry(), t.getZip()));
            if( i != transactions.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the transaction table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryCustomerTable(Connection conn,
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
        sb.append("FROM transactions ");

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
    public static void printCustomerTable(Connection conn){
        String query = "SELECT * FROM transactions;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("transactions %d,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8),
                        result.getString(9),
                        result.getString(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
