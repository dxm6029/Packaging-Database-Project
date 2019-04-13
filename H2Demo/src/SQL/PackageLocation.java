package SQL;

public class PackageLocation {

    int packageID;
    String location;
    String deliveryTime;

    public PackageLocation(int packageID, String location, String deliveryTime){
        this.packageID = packageID;
        this.location = location;
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        return "PackageLocation{" +
                "packageID=" + packageID +
                ", location='" + location + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }
}
