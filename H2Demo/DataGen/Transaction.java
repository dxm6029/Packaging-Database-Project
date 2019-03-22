
public class Transaction {
	final static String[] header = {"Send_Cust_ID", "pack_ID", "Cost", "Street#", "Str_Name", "Apt#", "City", "State", "ZIP"};
	static String[][] tran_matrix = new String[500][header.length];

	public static void init() {
		tran_matrix = Packages.genSimpleInt(0, 1, Integer.MAX_VALUE, tran_matrix);
		tran_matrix = Status.setFromCol(0, 1, Packages.p_matrix, tran_matrix);
		tran_matrix = Packages.genSimpleInt(2, 1, 30, tran_matrix);
		tran_matrix = Packages.genSimpleInt(3, 1, 6000, tran_matrix);
		tran_matrix = Status.setFromArrayRandomly(4, new String[] {"Jefferson Ave.", "Harrison St.", "James Ct.",
				"King Dr.", "Armstrong Ave.", "Hollis Lane", "Joy Ct.", "Washington Ave.", "Clark St.", "149th St.",
				"Canal St.", "Hicksville Road"},  
				tran_matrix);
		tran_matrix = Status.setFromArrayRandomly(5, new String[] {"1","2","3","4","5","6","7","8","9","10","23","44","85","34","99" 
				
		},tran_matrix);
		tran_matrix = Status.setFromArrayRandomly(6, new String[] {"Wantaugh","Massapequa","Copaigue","Quogue","Amagansett","Northampton",
				"Commack", "Hauppague", "Speonk", "Mattituck", "Sagaponack", "Wyandanch", "Ronkonkoma", "Yaphank"},tran_matrix);
		tran_matrix = Status.setFromArrayRandomly(7, new String[] {"New York", "New Jersey", "Maine", "Delaware", "Pennsylvania", 
				"Florida", "Georgia", "Louisiana", "California", "Oregon", "Washington", "Washington D.C.", "Texas", "Virginia",
				"West Virginia"},tran_matrix);
		tran_matrix = Packages.genSimpleInt(8, 10001, 70000, tran_matrix);
	}
}
