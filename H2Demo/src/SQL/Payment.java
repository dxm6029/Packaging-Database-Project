package SQL;


public class Payment {
    int paymentID;
    String type;

    public Payment(int payID, String type){
        this.paymentID = payID;
        this.type = type;
    }

    public Payment(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.type = data[1];
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
