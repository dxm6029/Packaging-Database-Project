public class MakesPayment {

    int customerID;
    int paymentID;

    public MakesPayment(int customerID, int transactionID){
        this.customerID = customerID;
        this.paymentID = transactionID;
    }

    public MakesPayment(String[] data){
        this.customerID = Integer.parseInt(data[0]);
        this.paymentID = Integer.parseInt(data[1]);
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
}
