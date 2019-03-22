public class MakesTransaction {

    int customerID;
    int transactionID;

    public MakesTransaction(int customerID, int transactionID){
        this.customerID = customerID;
        this.transactionID = transactionID;
    }

    public MakesTransaction(String[] data){
        this.customerID = Integer.parseInt(data[0]);
        this.transactionID = Integer.parseInt(data[1]);
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
}
