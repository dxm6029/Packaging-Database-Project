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
 * Class to make and manipulate the person table
 * @author scj
 *
 */
public class TransportationTable {

    /**
     * Reads a cvs file for data and adds them to the person table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populateTransportationFromCSV(Connection conn,
                                                            String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Transportation> transports = new ArrayList<Transportation>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                transports.add(new Transportation(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all transports
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createTransportationInsertSQL(transports);

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
     * Create the person table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createTransportationTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS packageTransportation(transportID int primary key, company varchar(20), packageID int foreign key)";

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
     * Adds a single Customer to the database
     *
     */
    public static void addTransportation(Connection conn,int transportID, String company, int packageID){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO transportation "
                        + "VALUES(%d,\'%s\',%d);",
                transportID, company, packageID);
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
     * @param people: list of Person objects to add
     *
     * @return
     */
    public static String createTransportationInsertSQL(ArrayList<Transportation> people){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO transaction (transactionID, company, packageID) VALUES");

        /**
         * For each person append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last person add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for(int i = 0; i < people.size(); i++){
            Transportation p = people.get(i);
            sb.append(String.format("(%d,\'%s\',%d)",
                    p.getTransportID(),p.getCompany(),  p.getDriverID()));
            if( i != people.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the person table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryTransportationTable(Connection conn,
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
        sb.append("FROM transportation ");

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
    public static void printTransportationTable(Connection conn){
        String query = "SELECT * FROM transportation;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("transportation %d,\'%s\',%d\n",
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
