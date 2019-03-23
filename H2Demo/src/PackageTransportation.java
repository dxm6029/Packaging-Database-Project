public class PackageTransportation {

    int packageID;
    int transportID;

    public PackageTransportation(int customerID, int transactionID){
        this.packageID = customerID;
        this.transportID = transactionID;
    }

    public PackageTransportation(String[] data){
        this.packageID = Integer.parseInt(data[0]);
        this.transportID = Integer.parseInt(data[1]);
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int customerID) {
        this.packageID = customerID;
    }

    public int getTransportID() {
        return transportID;
    }

    public void setTransportID(int transportID) {
        this.transportID = transportID;
    }
}
