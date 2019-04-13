package SQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PostalWorkerTable {

    /**
     * Reads a cvs file for data and adds them to the Worker table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */
    public static void populatePostalTableFromCSV(Connection conn,
                                                   String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<PostalWorker> worker = new ArrayList<PostalWorker>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                worker.add(new PostalWorker(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all Worker
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createPostalInsertSQL(worker);

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
     * Create the Worker table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createPostalTable(Connection conn){
        try {
            //FOR THE LOVE OF GOD UNDO THIS
            String q = "DROP TABLE IF EXISTS workers";
            Statement stmtt = conn.createStatement();
            stmtt.execute(q);
            String query = "CREATE TABLE IF NOT EXISTS workers(name VARCHAR(50), location VARCHAR(100), workerID INT PRIMARY KEY)";

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
     * Adds a single Worker to the database
     *
     */
    public static void addPostal(Connection conn, String name, String location, int workerID){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO workers "
                        + "VALUES(\'%s\', \'%s\', %d);",
                name, location, workerID );
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
     * @param worker: list of Worker objects to add
     *
     * @return
     */
    public static String createPostalInsertSQL(ArrayList<PostalWorker> worker){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO workers (name, location, workerID) VALUES");

        /**
         * If it is not the last Worker add a comma to seperate
         *
         * If it is the last Worker add a semi-colon to end the statement
         */
        for(int i = 0; i < worker.size(); i++){
            PostalWorker p = worker.get(i);
            sb.append(String.format("(\'%s\', \'%s\', %d)",
                    p.getName(), p.getLocation(), p.getWorkerID()));
            if( i != worker.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /**
     * Makes a query to the Worker table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryPostalTable(Connection conn,
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
        sb.append("FROM workers ");

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
    public static void printPostalTable(Connection conn){
        String query = "SELECT * FROM workers;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("workers %s, %s, %d\n",
                        result.getString(1),
                        result.getString(2),
                        result.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getPassword(String username, Connection conn) {
        String password = "tempstring";

        ArrayList<String> columns = new ArrayList<>();
        columns.add("workerId");
        ArrayList<String> whereClauses = new ArrayList<>();
        whereClauses.add("name = '" + username + "'");

        ResultSet result = queryPostalTable(conn, columns, whereClauses);

        try {
            if (result.next()) {
                password = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }

    public static String getName(String id, Connection conn) {
        String[] names;
        String fName = "";
        String lName = "";

        ArrayList<String> columns = new ArrayList<>();
        columns.add("name");
        ArrayList<String> whereClauses = new ArrayList<>();
        whereClauses.add("workerId = " + id);

        ResultSet resultSet = queryPostalTable(conn, columns, whereClauses);

        try {
            if (resultSet.next()) {
                names = resultSet.getString(1).split("_");
                fName = names[0];
                lName = names[1];
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fName + " " + lName;
    }

    public static int getWorkerNum(int workerId, Connection conn) {
        int count = 0;

        try {
            String query = "SELECT workerId FROM WORKERS";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                if (result.getInt(1) == workerId) {
                    return count;
                }

                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
