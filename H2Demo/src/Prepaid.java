public class Prepaid {

    int paymentID;
    double cost;
    boolean used;

    public Prepaid(int payID, double cost, boolean used){
        this.paymentID = payID;
        this.cost = cost;
        this.used = used;
    }

    public Prepaid(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.cost = Double.parseDouble(data[1]);
        this.used = Boolean.parseBoolean(data[2]);
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
