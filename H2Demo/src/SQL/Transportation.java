package SQL;

public class Transportation {

    int transportID;
    String company;
    int driverID;

    public Transportation(int transportID, String company, int driverID){
        this.transportID = transportID;
        this.company = company;
        this.driverID = driverID;
    }

    public Transportation(String[] data){
        this.transportID = Integer.parseInt(data[0]);
        this.company = data[1];
        this.driverID = Integer.parseInt(data[2]);
    }

    public int getTransportID() {
        return transportID;
    }

    public void setTransportID(int transportID) {
        this.transportID = transportID;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }
}
