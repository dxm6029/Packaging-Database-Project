import java.util.Random;

public class StatusGen {
	final static String[] header = {"pack_ID", "EDT", "Loc", "spec_tag", "inter/local", "delTime"};
	static String[][] s_matrix = new String[500][header.length];
	
	public static void init() {
		s_matrix = setFromCol(0,0,Packages.p_matrix, s_matrix);
		s_matrix = setFromArrayRandomly(1, new String[] {"2/9/2019", "2/10/2019", "2/11/2019"}, s_matrix);
		s_matrix = setFromArrayRandomly(2, new String[] {"McMurdo Station", "Seaford", "NYC", "The Big Apple"}, s_matrix);
		s_matrix = setFromArrayRandomly(3, new String[] {"Hazardous", "Fragile", "Hazardous and Fragile", "", "", ""}, s_matrix);
		s_matrix = setFromArrayRandomly(4, new String[] {"international", "local", "local", "", "", ""}, s_matrix);
		s_matrix = setColToVal(5, "pending", s_matrix);
	}
	
	public static String[][] setColToVal(int col, String Val, String[][] s_matrix) {
		for(int i = 0; i < s_matrix.length; i++)
			s_matrix[i][col] = Val;
		return s_matrix;
	}
	
	public static String[][] setFromArrayRandomly(int col, String[] options, String[][] s_matrix) {
		Random r = new Random();
		for(int i = 0; i < s_matrix.length; i++) {
			s_matrix[i][col] = options[r.nextInt(options.length)];
		}
		return s_matrix;
	}
	
	public static String[][] setFromCol(int fromCol, int toCol, String[][] fromMat, String[][] toMat) {
		String[] from = Packages.getCol(fromCol, fromMat);
		for(int i = 0; i < from.length; i++) {
			toMat[i][toCol] = from[i];
		}
		return toMat;
	}
}
