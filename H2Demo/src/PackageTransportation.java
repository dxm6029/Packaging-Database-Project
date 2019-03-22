public class PackageTransportation {

    int customerID;
    int transportID;

    public PackageTransportation(int customerID, int transactionID){
        this.customerID = customerID;
        this.transportID = transactionID;
    }

    public PackageTransportation(String[] data){
        this.customerID = Integer.parseInt(data[0]);
        this.transportID = Integer.parseInt(data[1]);
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getTransportID() {
        return transportID;
    }

    public void setTransportID(int transportID) {
        this.transportID = transportID;
    }
}
