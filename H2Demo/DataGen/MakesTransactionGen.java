import java.util.ArrayList;

public class MakesTransactionGen {
    
    CustomerGen customer;
    TransactionGen transaction;
    PaymentGen payment;
    ArrayList<Integer> customerID;
    ArrayList<Integer> transactionID;
    ArrayList<Integer> paymentID;
    
    public MakesTransactionGen(CustomerGen customer, TransactionGen transaction, PaymentGen payment){
        this.customer = customer;
        this.transaction = transaction;

    }
    
    public void init(){
        ArrayList<Integer> customerIDList = customer.customerID;
        ArrayList<Integer> transactionIDList = transaction.transactionID;
        ArrayList<Integer> paymentIDList = payment.paymentID;
        createCombos(customerIDList, transactionIDList, paymentIDList);
    }

    private void createCombos(ArrayList<Integer> customerIDList, ArrayList<Integer> transactionIDList, ArrayList<Integer> paymentIDList) {
        for(int i = 5; i < 500; i++){ // adds customer/transaction ids to this class's customer/transaction id list
            customerID.add(customerIDList.get(i));
            transactionID.add(transactionIDList.get(i-5));
            paymentID.add(paymentIDList.get(i-5));
        }
        for(int i = 5; i < 255; i++){
            customerID.add(customerIDList.get(i));
            transactionID.add(transactionIDList.get(i-5+500));
            paymentID.add(paymentIDList.get(i-5+500));
        }
    }

}
