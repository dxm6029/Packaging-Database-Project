import java.util.ArrayList;

public class Payment {
    int paymentID;
    double cost;
    String type;

    public Payment(int payID, double cost, String type){
        this.paymentID = payID;
        this.cost = cost;
        this.type = type;
    }

    public Payment(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.cost = Double.parseDouble(data[1]);
        this.type = data[2];
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
}
