import java.util.ArrayList;

public class CustomerGen {
	final static String[] header = {"name_first","name_last", "c_ID", "email", "Street#", "Str_Name", "Apt#", "City", "State", "Country", "ZIP"};
	static String[][] c_matrix = new String[500][header.length];

	public static void init() {
		c_matrix = StatusGen.setFromArrayRandomly(0, new String[] {"Allan", "Aaron", "Matt", "Ben", "Josh", "Joel", "Michael", "Ryan",
				"Brian", "Ricky", "Rick", "Patrick", "Kevin", "Noah", "Felix", "Isaiah"}, c_matrix);
		c_matrix = StatusGen.setFromArrayRandomly(1, new String[] {"Barzal","Bailey","Colliton","Deever","Nunes","Harwitt","Poulin",
				"Alexander-Parenteau", "Tavares", "Nilsson","Nielsen","Nelson","Ryan","Barreiros","Passino","White","Black"}, 
				c_matrix);
		c_matrix = Packages.genComplexInt(2, 10000, 99999, c_matrix);
		//email
		c_matrix = createEmail(3, c_matrix);
		c_matrix = Packages.genSimpleInt(4, 1, 9999, c_matrix);
		c_matrix = StatusGen.setFromArrayRandomly(5, new String[] {"Jefferson Ave.", "Harrison St.", "James Ct.",
				"King Dr.", "Armstrong Ave.", "Hollis Lane", "Joy Ct.", "Washington Ave.", "Clark St.", "149th St.",
				"Canal St.", "Hicksville Road"},
				c_matrix);
		c_matrix = StatusGen.setFromArrayRandomly(6, new String[] {"6D", "5G", "232", "56H", "1F", "1 FL", "2 FL", "5K", "7D", "SUITE 544", "SUITE 345",
				"8R", "9F", "24", "3 FL"}, c_matrix);
		c_matrix = StatusGen.setFromArrayRandomly(7, new String[] {"Wantaugh","Massapequa","Copaigue","Quogue","Amagansett","Northampton",
				"Commack", "Hauppague", "Speonk", "Mattituck", "Sagaponack", "Wyandanch", "Ronkonkoma", "Yaphank"},c_matrix);
		c_matrix = StatusGen.setFromArrayRandomly(8, new String[] {"New York", "New Jersey", "Maine", "Delaware", "Pennsylvania",
				"Florida", "Georgia", "Louisiana", "California", "Oregon", "Washington", "Washington D.C.", "Texas", "Virginia",
				"West Virginia"},c_matrix);
		c_matrix = StatusGen.setFromArrayRandomly(9, new String[] {"United States", "United Kingdom", "India", "China", "South Africa",
				"Denmark", "Canada", "New Zealand", "Mexico", "Spain", "Italy", "Germany", "Sweden", "France",
				"Australia"},c_matrix);
		c_matrix = Packages.genSimpleInt(10, 10001, 70000, c_matrix);
	}

	public static String[][] createEmail(int col, String[][] matrix){
		String[] pre = new String[] {"xXmetallicaFan1992Xx", "pdefran", "adsdf", "password1", "helloWorld",
				"qedoffifa", "ehofallat", "oduderrac", "issecosa", "magicstuff"};
		String[] num = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		String[] post = new String[]{"gmail.com", "rit.edu", "yahoo.com", "aol.com", "business.com"};
		ArrayList<String> combos = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				for(int k = 0; k < 5; k++){
					combos.add(pre[i] + num[j] + "@" +post[k]);
				}
			}
		}
		for(int i = 0; i < matrix.length; i++) {
			matrix[i][col] = combos.remove(0);
		}
		return matrix;
	}
}
