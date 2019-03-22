import java.util.ArrayList;

public class Package {

    String packageType;
    double weight;
    String deliveryType;
    int packageID;
    ArrayList<String> location;
    String expectedDelivery;
    String extraInfo;
    String deliveryTime;

    public Package(String packageType, double weight, String deliveryType, int packageID, String expectedDelivery,
                   String extraInfo, String deliveryTime){
        this.packageType = packageType;
        this.weight = weight;
        this.deliveryType = deliveryType;
        this.packageID = packageID;
        this.location = new ArrayList<>();
        this.expectedDelivery = expectedDelivery;
        this.extraInfo = extraInfo;
        this.deliveryTime = deliveryTime;

    }

    public Package(String[] data){
        this.packageType = data[0];
        this.weight = Double.parseDouble(data[1]);
        this.deliveryType = data[2];
        this.packageID = Integer.parseInt(data[3]);
//        this.location = ;
        this.expectedDelivery = data[4];
        this.extraInfo = data[5];
        this.deliveryTime = data[6];
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

    public String getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(String expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
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

}
