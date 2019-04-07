package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This is a sample main program. 
 * You will create something similar
 * to run your database.
 * 
 * @author scj
 *
 */
public class H2Main {

	//The connection to the database
	private Connection conn;

	
	/**
	 * Create a database connection with the given params
	 * @param location: path of where to place the database
	 * @param user: user name for the owner of the database
	 * @param password: password of the database owner
	 */
	public void createConnection(String location,
			                     String user,
			                     String password){
		try {
			
			//This needs to be on the front of your location
			String url = "jdbc:h2:" + location;
			
			//This tells it to use the h2 driver
			Class.forName("org.h2.Driver");
			
			//creates the connection
			conn = DriverManager.getConnection(url,
					                           user,
					                           password);
		} catch (SQLException | ClassNotFoundException e) {
			//You should handle this better
			e.printStackTrace();
		}
	}
	
	/**
	 * just returns the connection
	 * @return: returns class level connection
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * When your database program exits 
	 * you should close the connection
	 */
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts and runs the database
	 * @param args: not used but you can use them
	 */
	public static void main(String[] args) {
		H2Main demo = new H2Main();

		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";
		
		//Create the database connections, basically makes the database
		demo.createConnection(location, user, password);
		
		try {
			CustomerTable.createCustomerTable(demo.getConnection());
			CustomerTable.populateCustomerTableFromCSV(
					demo.getConnection(),
					"H2Demo/csv/Customer.csv");
			//TODO: add initialization for all other tables
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//TODO: prompt for queries
	}

	public static boolean getPassword(String email, String pass){
		H2Main demo = new H2Main();
		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";

		//Create the database connections, basically makes the database
		demo.createConnection(location, user, password);
		return CustomerTable.getPassword(email, demo.getConnection()).equals(pass);
	}

}
