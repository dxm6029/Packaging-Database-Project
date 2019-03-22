
public class Trucks {
	final static String[] header = {"truck_ID", "driver_ID", "tracking", "company"};
	static String[][] t_matrix = new String[500][header.length];
	
	public static void init() {
		t_matrix = Packages.genSimpleInt(0, 1, Integer.MAX_VALUE, t_matrix);
		t_matrix = Packages.genSimpleInt(1, 1, Integer.MAX_VALUE, t_matrix);
		t_matrix = StatusGen.setColToVal(2,"processing...", t_matrix);
		t_matrix = StatusGen.setFromArrayRandomly(3, new String[] {"Trucks, Inc.", "International Trucks, Inc.", "Rochester Trucks, Inc."}, t_matrix);
	}
	
}