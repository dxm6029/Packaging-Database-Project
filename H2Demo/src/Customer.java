import java.util.ArrayList;

/**
 * Hold data about a person
 * @author scj
 *
 */
public class Customer {

	String name;
	int customerID;
	String email;
	ArrayList<Integer> phoneNum;
	int streetNumber;
	String streetName;
	String apptNum;
	String city;
	String state;
	int zip;

	public Customer(String name, int custID, String email, int streetNum, String streetName, String apptNum,
					String city, String state, int zip){
		this.name = name;
		this.customerID = custID;
		this.email = email;
		this.phoneNum = new ArrayList<>();
		this.streetNumber = streetNum;
		this.streetName = streetName;
		this.apptNum = apptNum;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public Customer(String[] data){
		this.name = data[0];
		this.customerID = Integer.parseInt(data[1]);
		this.email = data[2];
//		this.phoneNum = ;
		this.streetNumber = Integer.parseInt(data[3]);
		this.streetName = data[4];
		this.apptNum = data[5];
		this.city = data[6];
		this.state = data[7];
		this.zip = Integer.parseInt(data[8]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Integer> getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(ArrayList<Integer> phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
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

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

}
