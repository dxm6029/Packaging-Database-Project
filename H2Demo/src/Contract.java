public class Contract{

    int paymentID;
    double cost;
    double total; // I can also change to float?
    int billDate;
    int totalPackageNum;

    public Contract(int payID, double cost, double total, int billDate, int totalPackageNum){
        this.paymentID = payID;
        this.cost = cost;
        this.total = total;
        this.billDate = billDate;
        this.totalPackageNum = totalPackageNum;
    }

    public Contract(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.cost = Double.parseDouble(data[1]);
        this.total = Double.parseDouble(data[2]);
        this.billDate = Integer.parseInt(data[3]);
        this.totalPackageNum = Integer.parseInt(data[4]);
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getBillDate() {
        return billDate;
    }

    public void setBillDate(int billDate) {
        this.billDate = billDate;
    }

    public int getTotalPackageNum() {
        return totalPackageNum;
    }

    public void setTotalPackageNum(int totalPackageNum) {
        this.totalPackageNum = totalPackageNum;
    }
}
