import java.util.ArrayList;

public class MakesTransactionGen {
    
    CustomerGen customer;
    TransactionGen transaction;
    ArrayList<Integer> customerID;
    ArrayList<Integer> transactionID;
    
    public MakesTransactionGen(CustomerGen customer, TransactionGen transaction){
        this.customer = customer;
        this.transaction = transaction;
    }
    
    public void init(){
        ArrayList<Integer> customerIDList = customer.customerID;
        ArrayList<Integer> transactionIDList = transaction.transactionID;
        createCombos(customerIDList, transactionIDList);
    }

    private void createCombos(ArrayList<Integer> customerIDList, ArrayList<Integer> transactionIDList) {
        for(int i = 5; i < 500; i++){
            customerID.add(customerIDList.get(i));
            transactionID.add(transactionIDList.get(i-5));
        }
        for(int i = 5; i < 255; i++){
            customerID.add(customerIDList.get(i));
            transactionIDList.add(i-5+500);
        }
    }

}
