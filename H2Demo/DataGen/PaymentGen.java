public class PaymentGen {
	final static String[] header = {"Pay_ID", "Cost", "Type", "Total", "BillDate", "TotPackagingNum", "CardHolderName", "CCV", "ExpDate", "isPrepaid"};
	static String[][] pay_matrix = new String[500][header.length];

	public static void init() {
		double d = Math.random();
		pay_matrix = Packages.genSimpleInt(0, 0, Integer.MAX_VALUE, pay_matrix);
		pay_matrix = Packages.genSimpleInt(1, 1, 20, pay_matrix);
		pay_matrix = StatusGen.setFromArrayRandomly(2, new String[]{"Contract", "Credit Card", "Prepaid"}, pay_matrix);
		pay_matrix = Packages.genSimpleInt(3, 10, 1000, pay_matrix);
		pay_matrix = StatusGen.setFromArrayRandomly(4, new String[]{"1/1/2019", "2/2/2019", "3/3/2019", "4/3/2019",
				"5/6/2019", "7/9/2019", "4/2/2019", "12/23/2019", "3/25/2019", "6/30/2019", "10/9/2019", "9/10/2019",
				"4/3/2019", "3/6/2019", "4/20/2019"}
				, pay_matrix);
		pay_matrix = Packages.genSimpleInt(5, 11111, 99999, pay_matrix);
		pay_matrix = StatusGen.setFromArrayRandomly(6, new String[] {"Allan", "Aaron", "Matt", "Ben", "Josh", "Joel", "Michael", "Ryan",
				"Brian", "Ricky", "Rick", "Patrick", "Kevin", "Noah", "Felix", "Isaiah"}, pay_matrix);
		pay_matrix = Packages.genSimpleInt(7, 100, 999, pay_matrix);
		pay_matrix = StatusGen.setFromArrayRandomly(8, new String[]{"1/1/2020", "2/2/2021", "3/3/2020", "4/3/2021",
				"5/6/2020", "7/9/2020", "4/2/2020", "12/23/2021", "3/25/2020", "6/30/2021", "10/9/2020", "9/10/2021",
				"4/3/2020", "3/6/2021", "4/20/2021"}
				, pay_matrix);
		pay_matrix = StatusGen.setFromArrayRandomly(9, new String[] {"no"}, pay_matrix);
		for(int i = 0; i < pay_matrix.length; i++) {
			if(pay_matrix[i][2].equals("Contract"))
				pay_matrix = setFieldsToNull(pay_matrix, i, 6,header.length-1);
				
			if(pay_matrix[i][2].equals("Credit Card")) {
				pay_matrix = setFieldsToNull(pay_matrix, i, 3,6);
				pay_matrix = setFieldsToNull(pay_matrix, i, header.length-1,header.length-1);
			}
				
			if(pay_matrix[i][2].equals("Prepaid")) {
				pay_matrix = setFieldsToNull(pay_matrix, i, 3, header.length-1);
				pay_matrix[i][9] = "yes";
			}
		}
	}
	
	public static String[][] setFieldsToNull(String[][] matrix, int row, int colLow, int colHigh){
		for(int i = colLow; i < colHigh; i++) {
			matrix[row][i] = "null";
		}
		return matrix;
	}

}
