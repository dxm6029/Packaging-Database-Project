public class Prepaid {

    int paymentID;
    double cost;
    String type;
    boolean used;

    public Prepaid(int payID, double cost, String type, boolean used){
        this.paymentID = payID;
        this.cost = cost;
        this.type = type;
        this.used = used;
    }

    public Prepaid(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.cost = Double.parseDouble(data[1]);
        this.type = data[2];
        this.used = Boolean.parseBoolean(data[3]);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
