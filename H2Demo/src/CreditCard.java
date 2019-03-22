public class CreditCard {

    int paymentID;
    double cost;
    String type;
    String cardholderName;
    int cardNum;
    int cvv;
    int expirationDate;


    public CreditCard(int payID, double cost, String type, String name, int cardNum, int cvv, int expirationDate){
        this.paymentID = payID;
        this.cost = cost;
        this.type = type;
        this.cardholderName = name;
        this.cardNum = cardNum;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public CreditCard(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.cost = Double.parseDouble(data[1]);
        this.type = data[2];
        this.cardholderName = data[3];
        this.cardNum = Integer.parseInt(data[4]);
        this.cvv = Integer.parseInt(data[5]);
        this.expirationDate = Integer.parseInt(data[6]);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }
}
