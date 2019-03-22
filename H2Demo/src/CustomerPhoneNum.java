public class CustomerPhoneNum {

    int customerID;
    int phoneNum;

    public CustomerPhoneNum(int customerID, int phoneNum){
        this.customerID = customerID;
        this.phoneNum = phoneNum;
    }

    public CustomerPhoneNum(String[] data){
        this.customerID = Integer.parseInt(data[0]);
        this.phoneNum = Integer.parseInt(data[1]);
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }
}
