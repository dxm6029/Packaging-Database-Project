
public class CustomerGen {
	final static String[] header = {"name_first","name_last", "c_ID", "email_pre","email#", "email_post", "phone#", "Street#", "Str_Name", "Apt#", "City", "State", "ZIP"};
	static String[][] c_matrix = new String[500][header.length];

	public static void init() {
		c_matrix = Status.setFromArrayRandomly(0, new String[] {"Allan", "Aaron", "Matt", "Ben", "Josh", "Joel", "Michael", "Ryan",
				"Brian", "Ricky", "Rick", "Patrick", "Kevin", "Noah", "Felix", "Isaiah"}, c_matrix);
		c_matrix = Status.setFromArrayRandomly(1, new String[] {"Barzal","Bailey","Colliton","Deever","Nunes","Harwitt","Poulin",
				"Alexander-Parenteau", "Tavares", "Nilsson","Nielsen","Nelson","Ryan","Barreiros","Passino","White","Black"}, 
				c_matrix);
		c_matrix = Packages.genSimpleInt(2, 10000, 99999, c_matrix);
		c_matrix = Status.setFromArrayRandomly(3, new String[] {"xXmetallicaFan1992Xx", "pdefran", "adsdf", "password1", "helloWorld",
				"qedoffifa", "ehofallat", "oduderrac", "issecosa"},
				c_matrix);
		c_matrix = Packages.genSimpleInt(4, 1, 100, c_matrix);
		c_matrix = Status.setFromArrayRandomly(5, new String[] {"gmail.com", "rit.edu", "yahoo.com", "ymail.com"}, c_matrix);
		c_matrix = Packages.genSimpleInt(6, 1111111111, Integer.MAX_VALUE, c_matrix);
		c_matrix = Packages.genSimpleInt(7, 1, 9999, c_matrix);
		c_matrix = Status.setFromArrayRandomly(8, new String[] {"Jefferson Ave.", "Harrison St.", "James Ct.",
				"King Dr.", "Armstrong Ave.", "Hollis Lane", "Joy Ct.", "Washington Ave.", "Clark St.", "149th St.",
				"Canal St.", "Hicksville Road"},
				c_matrix);
		c_matrix = Packages.genSimpleInt(9, 1, 100, c_matrix);
		c_matrix = Status.setFromArrayRandomly(10, new String[] {"Wantaugh","Massapequa","Copaigue","Quogue","Amagansett","Northampton",
				"Commack", "Hauppague", "Speonk", "Mattituck", "Sagaponack", "Wyandanch", "Ronkonkoma", "Yaphank"},c_matrix);
		c_matrix = Status.setFromArrayRandomly(11, new String[] {"New York", "New Jersey", "Maine", "Delaware", "Pennsylvania", 
				"Florida", "Georgia", "Louisiana", "California", "Oregon", "Washington", "Washington D.C.", "Texas", "Virginia",
				"West Virginia"},c_matrix);
		c_matrix = Packages.genSimpleInt(12, 10001, 70000, c_matrix);
	}
}
