public class Contract{

    int paymentID;
    double total; // I can also change to float?
    int billDate;
    int totalPackageNum;

    public Contract(int payID, double total, int billDate, int totalPackageNum){
        this.paymentID = payID;
        this.total = total;
        this.billDate = billDate;
        this.totalPackageNum = totalPackageNum;
    }

    public Contract(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.total = Double.parseDouble(data[1]);
        this.billDate = Integer.parseInt(data[2]);
        this.totalPackageNum = Integer.parseInt(data[3]);
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
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
