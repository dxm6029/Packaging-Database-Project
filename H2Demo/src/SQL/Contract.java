package SQL;

public class Contract{

    int paymentID;
    int billDate;
    int totalPackageNum;

    public Contract(int payID, int billDate, int totalPackageNum){
        this.paymentID = payID;
        this.billDate = billDate;
        this.totalPackageNum = totalPackageNum;
    }

    public Contract(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.billDate = Integer.parseInt(data[1]);
        this.totalPackageNum = Integer.parseInt(data[2]);
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
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
