package SQL;

public class Contract{

    int paymentID;
    String billDate;
    int totalPackageNum;
    double paid;

    public Contract(int payID, String billDate, int totalPackageNum){
        this.paymentID = payID;
        this.billDate = billDate;
        this.totalPackageNum = totalPackageNum;
        this.paid = 0;
    }

    public Contract(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.billDate = data[1];
        this.totalPackageNum = Integer.parseInt(data[2]);
        this.paid = Double.parseDouble(data[3]);
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public int getTotalPackageNum() {
        return totalPackageNum;
    }

    public void setTotalPackageNum(int totalPackageNum) {
        this.totalPackageNum = totalPackageNum;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }
}
