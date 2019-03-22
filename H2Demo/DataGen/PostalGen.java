
public class PostalGen {
	final static String[] header = {"workerID", "name", "DOB", "phone #", "location", "bank #"};
	static String[][] postal_matrix = new String[500][header.length];
	
	public static void init() {
		postal_matrix = StatusGen.setFromCol(3,0,Packages.p_matrix, postal_matrix);
		postal_matrix = StatusGen.setFromArrayRandomly(1, new String[] {"Jeff", "Joe", "Jerry", "Jeremy", "Jeremiah", "Patrick!"}, postal_matrix);
		postal_matrix = StatusGen.setFromArrayRandomly(2,
				new String[] {"2/20/1999", "1/19/1999", "5/23/1977", "6/19/1980", "8/11/1955", "6/11/1945", "5/5/1955"},	
				postal_matrix);
		postal_matrix = Packages.genSimpleInt(3, 100000000, 999999999, postal_matrix);
		postal_matrix = StatusGen.setFromCol(2, 4, StatusGen.s_matrix, postal_matrix);
		postal_matrix = Packages.genSimpleInt(5, 1, Integer.MAX_VALUE, postal_matrix);
	}
}
