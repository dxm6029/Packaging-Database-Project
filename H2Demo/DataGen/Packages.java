import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class Packages {
	//elements listed in logical order, ie col 0 is p_id
	final static String[] header = {"pack_ID", "pack_type", "delivery_type", "work_ID", "weight"};
	static String[][] p_matrix = new String[500][header.length];
	
	public static void main(String[] args) {
		init();
		StatusGen.init();
		PostalGen.init();
		Trucks.init();
		TransactionGen.init();
		CustomerGen.init();
		PaymentGen.init();
		
		String output = display(header, p_matrix);
		write(output, "Packages.csv");
		
		output = display(StatusGen.header, StatusGen.s_matrix);
		write(output, "Statuses.csv");
		
		output = display(PostalGen.header, PostalGen.postal_matrix);
		write(output, "Postals.csv");
		
		output = display(Trucks.header, Trucks.t_matrix);
		write(output, "Trucks.csv");
		
		output = display(TransactionGen.header, TransactionGen.tran_matrix);
		write(output, "Transactions.csv");
		
		output = display(CustomerGen.header, CustomerGen.c_matrix);
		write(output, "Customers.csv");
		
		output = display(PaymentGen.header, PaymentGen.pay_matrix);
		write(output, "Payments.csv");
	}
	
	public static void write(String output, String filename) {
		try (PrintWriter writer = new PrintWriter(new File(filename))) {
		      writer.write(output);
		}
		catch(Exception e) {e.getMessage();}
		
	}
	
	public static void init(){
		p_matrix = genSimpleInt(0, 1, Integer.MAX_VALUE, p_matrix);
		p_matrix = genSimpleInt(3, 1, Integer.MAX_VALUE, p_matrix);
		p_matrix = genPType(1, p_matrix);
		p_matrix = genDType(2, p_matrix);
		p_matrix = genSimpleInt(4, 1, 20, p_matrix);
	}
	
	public static String[] getCol(int col, String[][] p_matrix) {
		String[] ret = new String[p_matrix.length];
		for(int i = 0; i < p_matrix.length; i++) {
			ret[i] = p_matrix[i][col];
		}
		return ret;
	}
	
	public static String[][] genDType(int col, String[][] p_matrix) {
		Random rand = new Random();
		for(int i = 0; i < p_matrix.length; i++) {
			int r = rand.nextInt(4);
			switch(r) {
			case 0:
				p_matrix[i][col] = "express";
				break;
			case 1:
				p_matrix[i][col] = "two-day";
				break;
			case 2:
				p_matrix[i][col] = "snail mail";
				break;
			case 3:
				p_matrix[i][col] = "relaxed";
				break;
			}
		}
		return p_matrix;
	}
	
	public static String[][] genPType(int col, String[][] p_matrix) {
		Random rand = new Random();
		for(int i = 0; i < p_matrix.length; i++) {
			int r = rand.nextInt(4);
			switch(r) {
			case 0:
				p_matrix[i][col] = "flat";
				break;
			case 1:
				p_matrix[i][col] = "small";
				break;
			case 2:
				p_matrix[i][col] = "medium";
				break;
			case 3:
				p_matrix[i][col] = "large";
				break;
			}
		}
		return p_matrix;
	}
	
	public static String[][] genSimpleInt(int col, int min, int max, String[][] p_matrix){
		Random rand = new Random();
		for(int i = 0; i < p_matrix.length; i++) {
			p_matrix[i][col] = Integer.toString(rand.nextInt(max) + min);
		}
		return p_matrix;
	}
	
	public static String display(String[] header, String[][] p_matrix) {
		String out = "";
		for(String h : header) {
			out += h + ",";
		}
		out = out.substring(0, out.length() - 1);
		out += "\n";
		for(int i = 0; i < p_matrix.length; i++) {
			for(int j = 0; j < p_matrix[0].length; j++) {
				out += p_matrix[i][j] + ",";
			}
			out = out.substring(0, out.length() - 1);
			out += "\n";
		}
		return out;
	}
}
