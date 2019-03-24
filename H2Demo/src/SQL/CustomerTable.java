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
public class CustomerTable {

	/**
	 * Reads a cvs file for data and adds them to the person table
	 * 
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName
	 * @throws SQLException
	 */
	public static void populateCustomerTableFromCSV(Connection conn,
			                                      String fileName)
			                                    		  throws SQLException{
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 * 
		 * You can do the reading and adding to the table in one
		 * step, I just broke it up for example reasons
		 */
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			br.readLine();
			while((line = br.readLine()) != null){
				String[] split = line.split(",");
				customers.add(new Customer(split));
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
		String sql = createCustomerInsertSQL(customers);
		
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
	public static void createCustomerTable(Connection conn){
		try {
			//FOR THE LOVE OF GOD UNDO THIS
			String q = "DROP TABLE IF EXISTS customer";
			Statement stmtt = conn.createStatement();
			stmtt.execute(q);
			String query = "CREATE TABLE IF NOT EXISTS customer(fName VARCHAR(255),lName VARCHAR(255),customerID INT PRIMARY KEY,email VARCHAR(50),streetNumber VARCHAR(50),streetName VARCHAR(225),apptNum VARCHAR(50),city VARCHAR(100), state VARCHAR(100), country VARCHAR(100), zip VARCHAR(100))";
			
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
	public static void addCustomer(Connection conn, String fname, String lname, int custID, String email, int streetNum,
								 String streetName, String apptNum, String city, String state, String country, String zip){
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO customer "
						+ "VALUES(\'%s\', \'%s\',%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\', \'%s\', \'%s\');",
						fname, lname, custID, email, streetNum, streetName, apptNum, city, state, country, zip );
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
	public static String createCustomerInsertSQL(ArrayList<Customer> people){
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, 
		 * tells it the table to add it to
		 * the order of the data in reference 
		 * to the columns to ad dit to
		 */
		sb.append("INSERT INTO customer (fname, lname, customerID, email, streetNumber, streetName, apptNum, city, " +
				"state, country, zip) VALUES");
		
		/**
		 * For each person append a (id, first_name, last_name, MI) tuple
		 * 
		 * If it is not the last person add a comma to seperate
		 * 
		 * If it is the last person add a semi-colon to end the statement
		 */
		for(int i = 0; i < people.size(); i++){
			Customer p = people.get(i);
			sb.append(String.format("(\'%s\', \'%s\', %d, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\')",
					p.getFnameName(), p.getLnameName(), p.getCustomerID(), p.getEmail(), p.getStreetNumber(),
					p.getStreetName(), p.getApptNum(), p.getCity(), p.getState(), p.getCountry(), p.getZip()));
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
		sb.append("FROM customer ");
		
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
	public static void printCustomerTable(Connection conn){
		String query = "SELECT * FROM customer;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("customer %s,%s,%d,%s,%s,%s,%s,%s,%s,%s,%s\n",
						          result.getString(1),
						          result.getString(2),
						          result.getInt(3),
						          result.getString(4),
								  result.getString(5),
								  result.getString(6),
								  result.getString(7),
								  result.getString(8),
								  result.getString(9),
								  result.getString(10),
							      result.getString(11));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String getPassword(String email, Connection conn){
		String password = "No password found!";
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("CustomerID");

		/**
		 * Conditionals
		 */
		ArrayList<String> whereClauses = new ArrayList<String>();
		whereClauses.add("email = \'" + email +"\'");
		/**
		 * query and get the result set
		 *
		 * parse the result set and print it
		 *
		 * Notice not all of the columns are here because
		 * we limited what to show in the query
		 */
		ResultSet results2 = CustomerTable.queryCustomerTable(
				conn,
				columns,
				whereClauses);
		try{
			if(results2.next())
				password =  results2.getString(1);}catch(Exception e){System.out.print(e.toString());}
		return password;
	}
}
