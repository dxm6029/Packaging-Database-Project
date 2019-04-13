package SQL;

public class CreditCard {

    int paymentID;
    String cardholderName;
    String cardNum;
    String cvv;
    String expirationDate;


    public CreditCard(int payID, String name, String cardNum, String cvv, String expirationDate){
        this.paymentID = payID;
        this.cardholderName = name;
        this.cardNum = cardNum;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public CreditCard(String[] data){
        this.paymentID = Integer.parseInt(data[0]);
        this.cardholderName = data[1];
        this.cardNum = data[2];
        this.cvv = data[3];
        this.expirationDate = data[4];
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNum(){
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
