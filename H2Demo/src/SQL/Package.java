package SQL;

import java.util.ArrayList;

public class Package {

    String packageType;
    double weight;
    String deliveryType;
    int packageID;
    ArrayList<String> location;
    String startedDelivery;
    String extraInfo;
    String deliveryTime;
    int transactionID;

    public Package(String packageType, double weight, String deliveryType, int packageID, String startedDelivery,
                   String extraInfo, String deliveryTime, int transactionID){
        this.packageType = packageType;
        this.weight = weight;
        this.deliveryType = deliveryType;
        this.packageID = packageID;
        this.location = new ArrayList<>();
        this.startedDelivery = startedDelivery;
        this.extraInfo = extraInfo;
        this.deliveryTime = deliveryTime;
        this.transactionID = transactionID;

    }

    public Package(String[] data){
        this.packageType = data[0];
        this.weight = Double.parseDouble(data[1]);
        this.deliveryType = data[2];
        this.packageID = Integer.parseInt(data[3]);
        this.startedDelivery = data[4];
        this.extraInfo = data[5];
        this.deliveryTime = data[6];
        this.transactionID = Integer.parseInt(data[7]);
    }


    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public ArrayList<String> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }

    public String getStartedDelivery() {
        return startedDelivery;
    }

    public void setStartedDelivery(String expectedDelivery) {
        this.startedDelivery = expectedDelivery;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
}
