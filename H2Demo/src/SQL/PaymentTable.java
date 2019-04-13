package SQL;

import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PaymentTable {
    /**
     * Reads a cvs file for data and adds them to the payment table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populatePaymentTableFromCSV(Connection conn,
                                                   String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Payment> payment = new ArrayList<Payment>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                payment.add(new Payment(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all payment
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createPaymentInsertSQL(payment);

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
     * Create the payment table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createPaymentTable(Connection conn){
        try {
            //FOR THE LOVE OF GOD UNDO THIS
            String q = "DROP TABLE IF EXISTS payment";
            Statement stmtt = conn.createStatement();
            stmtt.execute(q);
            String query = "CREATE TABLE IF NOT EXISTS payment(paymentID INT PRIMARY KEY, type VARCHAR(15))";

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
     * Adds a single payment to the database
     *
     */
    public static void addPayment(Connection conn, int paymentID, String type){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO payment "
                        + "VALUES(%d, \'%s\');",
                paymentID, type);
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
     * This creates an sql statement to do a bulk add of people
     *
     * @param payments: list of payment objects to add
     *
     * @return
     */
    public static String createPaymentInsertSQL(ArrayList<Payment> payments){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO payment (paymentID, type) VALUES");

        /**
         * If it is not the last payment add a comma to seperate
         *
         * If it is the last payment add a semi-colon to end the statement
         */
        for(int i = 0; i < payments.size(); i++){
            Payment p = payments.get(i);
            sb.append(String.format("(%d, \'%s\')",
                    p.getPaymentID(), p.getType()));
            if( i != payments.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the payment table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryPaymentTable(Connection conn,
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
        sb.append("FROM payment ");

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
    public static void printPaymentTable(Connection conn){
        String query = "SELECT * FROM payment;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("payment %d, %s\n",
                        result.getInt(1),
                        result.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String getTransactionPaymentType(int transactionId, Connection conn) {
        String paymentType = "";

        String query = "SELECT type FROM payment WHERE paymentId IN " +
                "(SELECT paymentId FROM makesTransaction WHERE transactionId = " + transactionId + ");";

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {
                paymentType = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentType;
    }
}
