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
		
		//Hard drive location of the database
		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";
		
		//Create the database connections, basically makes the database
		demo.createConnection(location, user, password);
		
		try {
			
			/**
			 * Creates a sample Person table 
			 * and populates it from a csv file
			 */
			CustomerTable.createCustomerTable(demo.getConnection());
			CustomerTable.populateCustomerTableFromCSV(
					demo.getConnection(),
					"csv/CustomerEx.csv");
			
			/**
			 * Just displays the table
			 */
			CustomerTable.printCustomerTable(demo.getConnection());
			
			/**
			 * Runs a basic query on the table
			 */
			System.out.println("\n\nPrint results of SELECT * FROM Customers");
			ResultSet results = CustomerTable.queryCustomerTable(
					                     demo.getConnection(),
					                     new ArrayList<String>(),
					                     new ArrayList<String>());
			
			/**
			 * Iterates the Result set
			 * 
			 * Result Set is what a query in H2 returns
			 * 
			 * Note the columns are not 0 indexed
			 * If you give no columns it will return them in the
			 * order you created them. To gaurantee order list the columns
			 * you want
			 */
			while(results.next()){
				System.out.printf("Customer: %s, %s,%d,%s,%d,%s,%s,%s,%s,%d",
						          results.getString(1),
						          results.getString(2),
						          results.getInt(3),
						          results.getString(4),
						          results.getInt(5),
									results.getString(6),
							      results.getString(7),
							      results.getString(8),
							      results.getString(9),
							      results.getInt(10));

			}
			/**
			 * A more complex query with columns selected and 
			 * addition conditions
			 */
			System.out.println("\n\nPrint results of SELECT "
					+ " State, zip, "
					+ " FROM customer "
					+ "WHERE zip = \'55961\' ");
			
			/**
			 * This is one way to do this, but not the only
			 * 
			 * Create lists to make the whole thing more generic or
			 * you can just construct the whole query here 
			 */
			ArrayList<String> columns = new ArrayList<String>();
			columns.add("state");
			columns.add("zip");
			
			/**
			 * Conditionals
			 */
			ArrayList<String> whereClauses = new ArrayList<String>();
			whereClauses.add("zip = \'55961\'");
			/**
			 * query and get the result set
			 * 
			 * parse the result set and print it
			 * 
			 * Notice not all of the columns are here because
			 * we limited what to show in the query
			 */
			ResultSet results2 = CustomerTable.queryCustomerTable(
                    demo.getConnection(),
                    columns,
                    whereClauses);
			System.out.println(results2.toString());
			while(results2.next()){
				System.out.printf("Customer: %s, %d",
					results2.getString(1),
					results2.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
