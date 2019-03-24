package SQL;

public class Prepaid {

    int paymentID;
    boolean used;

    public Prepaid(int payID, double cost, boolean used){
        this.paymentID = payID;
        this.used = used;
    }

    public Prepaid(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.used = Boolean.parseBoolean(data[2]);
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
