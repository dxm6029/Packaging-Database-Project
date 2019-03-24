package SQL;

public class MakesTransaction {

    int customerID;
    int transactionID;
    int paymentID;

    public MakesTransaction(int customerID, int transactionID, int paymentID){
        this.customerID = customerID;
        this.transactionID = transactionID;
        this.paymentID = paymentID;
    }

    public MakesTransaction(String[] data){
        this.customerID = Integer.parseInt(data[0]);
        this.transactionID = Integer.parseInt(data[1]);
        this.paymentID = Integer.parseInt(data[2]);
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

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
}
