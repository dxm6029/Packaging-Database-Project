public class Transaction {

    int transactionID;
    double cost;
    String rfName;
    String lrName;
    int streetNum;
    String streetName;
    String apptNum;
    String city;
    String state;
    String country;
    int zip;



    public Transaction(int transactionID, double cost, String rfName, String lrName, int streetNum, String streetName,
            String apptNum, String city, String state, String country, int zip){
        this.transactionID = transactionID;
        this.cost = cost;
        this.rfName = rfName;
        this.lrName = lrName;
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.apptNum = apptNum;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
    }

    public Transaction(String[] data){
        this.transactionID = Integer.parseInt(data[0]);
        this.cost = Integer.parseInt(data[1]);
        this.rfName = data[2];
        this.lrName = data[3];
        this.streetNum = Integer.parseInt(data[4]);
        this.streetName = data[5];
        this.apptNum = data[6];
        this.city = data[7];
        this.state = data[8];
        this.country = data[9];
        this.zip = Integer.parseInt(data[10]);
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRfName() {
        return rfName;
    }

    public void setRfName(String rfName) {
        this.rfName = rfName;
    }

    public String getLrName() {
        return lrName;
    }

    public void setLrName(String lrName) {
        this.lrName = lrName;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getApptNum() {
        return apptNum;
    }

    public void setApptNum(String apptNum) {
        this.apptNum = apptNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
