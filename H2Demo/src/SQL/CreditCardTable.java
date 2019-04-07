package SQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CreditCardTable {

    /**
     * Reads a cvs file for data and adds them to the credit card table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populateCreditFromCSV(Connection conn,
                                                    String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                creditCards.add(new CreditCard(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all credit
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createCreditInsertSQL(creditCards);

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
     * Create the credit card table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createCreditTable(Connection conn){
        try {
            //FOR THE LOVE OF GOD UNDO THIS
            String q = "DROP TABLE IF EXISTS credit";
            Statement stmtt = conn.createStatement();
            stmtt.execute(q);
            String query = "CREATE TABLE IF NOT EXISTS credit(paymentID INT PRIMARY KEY, cardholderName VARCHAR(20), cardNum VARCHAR(20), cvv INT, expirationDate VARCHAR(20))";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            //System.out.println(query +"\n\n\n");
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a single card to the database
     *
     */
    public static void addCredit(Connection conn, int paymentID, String cardholderName, String cardNum, int cvv,
                                   String expirationDate){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO credit "
                        + "VALUES(%d, \'%s\', \'%s\', %d, \'%s\');",
                paymentID, cardholderName, cardNum, cvv, expirationDate);
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
     * This creates an sql statement to do a bulk add of cards
     *
     * @param cards: list of credit card objects to add
     *
     * @return
     */
    public static String createCreditInsertSQL(ArrayList<CreditCard> cards){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO credit (paymentID, cardholderName, cardNum, cvv, expirationDate) VALUES");

        /**
         * For each credit append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last credit add a comma to seperate
         *
         * If it is the last credit add a semi-colon to end the statement
         */
        for(int i = 0; i < cards.size(); i++){
            CreditCard c = cards.get(i);
            sb.append(String.format("(%d, \'%s\', \'%s\', %d, \'%s\')",
                    c.getPaymentID(), c.getCardholderName(), c.getCardNum(), c.getCvv(), c.getExpirationDate()));
            if( i != cards.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the credit table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryCreditTable(Connection conn,
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
        sb.append("FROM credit ");

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
        //System.out.println("Query: " + sb.toString());
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
    public static void printCreditTable(Connection conn){
        String query = "SELECT * FROM credit;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("credit %d, %s, %s, %d, %s\n",
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
