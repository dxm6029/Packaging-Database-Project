package SQL;

public class Package {

    String packageType;
    double weight;
    String deliveryType;
    int packageID;
    String location;
    String startedDelivery;
    String extraInfo;
    String deliveryTime;
    int transactionID;

    public Package(String packageType, double weight, String deliveryType, int packageID, String location, String startedDelivery,
                   String extraInfo, String deliveryTime, int transactionID){
        this.packageType = packageType;
        this.weight = weight;
        this.deliveryType = deliveryType;
        this.packageID = packageID;
        this.location = location;
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
        this.location = data[4];
        this.startedDelivery = data[5];
        this.extraInfo = data[6];
        this.deliveryTime = data[7];
        this.transactionID = Integer.parseInt(data[8]);
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

    @Override
    public String toString() {
        String deliverTime;

        if (deliveryTime.equals("null")) {
            deliverTime = "Not Yet Delivered";
        }
        else {
            deliverTime = deliveryTime;
        }
        weight = (double) Math.round(weight * 100)/100;
        return "PackageID: " + packageID + "\nTransactionID: " + transactionID + "\n\nType: " + packageType +
                " Weight: " + weight + "lbs.\nExtra Info: " + extraInfo + "\n\nStarted Delivery: " + startedDelivery
                + "\nDelivery Time: " + deliverTime + "\n%Last Checked In At: " + location;
    }
}
