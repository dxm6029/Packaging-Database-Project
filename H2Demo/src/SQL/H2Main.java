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
					demo.getConnection(), "H2Demo/csv/Customer.csv");
			MakesTransactionTable.createMakesTransactionTable(demo.getConnection());
			MakesTransactionTable.populateMakesTransactionTableFromCSV(
					demo.getConnection(), "H2Demo/csv/MakesTransaction.csv");
			PackageTable.createPackageTable(demo.getConnection());
			PackageTable.populatePackageTableFromCSV(
					demo.getConnection(), "H2Demo/csv/Package.csv");
			TransactionTable.createTransactionsTable(demo.getConnection());
			TransactionTable.populateTransactionTableFromCSV(
					demo.getConnection(),"H2Demo/csv/Transaction.csv");
			ContractTable.createContractTable(demo.getConnection());
			ContractTable.populateContractTableFromCSV(
					demo.getConnection(), "H2Demo/csv/Contract.csv");
			CreditCardTable.createCreditTable(demo.getConnection());
			CreditCardTable.populateCreditFromCSV(
					demo.getConnection(), "H2Demo/csv/CreditCard.csv");
			PrepaidTable.createPrepaidTable(demo.getConnection());
			PrepaidTable.populatePrepaidTableFromCSV(
					demo.getConnection(), "H2Demo/csv/Prepaid.csv");
			PostalWorkerTable.createPostalTable(demo.getConnection());
			PostalWorkerTable.populatePostalTableFromCSV(
					demo.getConnection(), "H2Demo/csv/PostalWorker.csv");
			TransportationTable.createTransportationTable(demo.getConnection());
			TransportationTable.populateTransportationFromCSV(
					demo.getConnection(), "H2Demo/csv/Transportation.csv");
			PaymentTable.createPaymentTable(demo.getConnection());
			PaymentTable.populatePaymentTableFromCSV(
					demo.getConnection(), "H2Demo/csv/Payment.csv");
			PackageTransportationTable.createPackageTransportationTable(demo.getConnection());
			PackageTransportationTable.populatePackageTransportationFromCSV(
					demo.getConnection(), "H2Demo/csv/PackageTransportation.csv");
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
		if(CustomerTable.getPassword(email, demo.getConnection()).equals(pass)){
			return true;
		}
		//check if worker
		else{
			return false;
		}
	}

	public static String getName(String customerID){
		H2Main demo = new H2Main();
		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";

		//Create the database connections, basically makes the database
		demo.createConnection(location, user, password);
		return CustomerTable.getName(customerID, demo.getConnection());
	}

	public static ArrayList<Integer> getPackageIds(int customerId) {
		H2Main demo = new H2Main();
		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";

		demo.createConnection(location, user, password);
		return PackageTable.getCustomerPackages(customerId, demo.getConnection());
	}

	public static ArrayList<Integer> getTransactionIds(int customerId) {
		H2Main demo = new H2Main();
		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";

		demo.createConnection(location, user, password);

		return MakesTransactionTable.getCustomerTransactions(customerId, demo.getConnection());
	}

	public static Transaction getTransactionInfo(int transactionId) {
		H2Main demo = new H2Main();
		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";

		demo.createConnection(location, user, password);

		return TransactionTable.getTransactionInfo(transactionId, demo.getConnection());
	}

	public static String getTransactionPaymentType(int transactionId) {
		H2Main demo = new H2Main();
		String location = "./h2demo/h2demo";
		String user = "me";
		String password = "password";

		demo.createConnection(location, user, password);

		return PaymentTable.getTransactionPaymentType(transactionId, demo.getConnection());
	}
}
