package SQL;

public class Transaction {

    int transactionID;
    String rfName;
    String lrName;
    String streetNum;
    String streetName;
    String apptNum;
    String city;
    String state;
    String country;
    String zip;



    public Transaction(int transactionID, String rfName, String lrName, String streetNum, String streetName,
            String apptNum, String city, String state, String country, String zip){
        this.transactionID = transactionID;
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
        this.rfName = data[1];
        this.lrName = data[2];
        this.streetNum = data[3];
        this.streetName = data[4];
        this.apptNum = data[5];
        this.city = data[6];
        this.state = data[7];
        this.country = data[8];
        this.zip = data[9];
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
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

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
