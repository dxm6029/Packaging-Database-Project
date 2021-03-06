package SQL;

public class Prepaid {

    int paymentID;
    boolean used; // will need to make a string in the end so it can be inserted and not inserted from table

    public Prepaid(int payID, double cost, boolean used){
        this.paymentID = payID;
        this.used = used;
    }

    public Prepaid(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.used = Boolean.parseBoolean(data[1]);
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
