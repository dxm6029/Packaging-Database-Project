package SQL;

import java.util.ArrayList;

/**
 * Hold data about a person
 * @author scj
 *
 */
public class Customer {

	String fname;
	String lname;
	int customerID;
	String email;
	int streetNumber;
	String streetName;
	String apptNum;
	String city;
	String state;
	String country;
	int zip;

	public Customer(String fname, String lname, int custID, String email, int streetNum, String streetName, String apptNum,
					String city, String state, String country, int zip){
		this.fname = fname;
		this.lname = lname;
		this.customerID = custID;
		this.email = email;
		this.streetNumber = streetNum;
		this.streetName = streetName;
		this.apptNum = apptNum;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
	}

	public Customer(String[] data){
		this.fname = data[0];
		this.lname = data[1];
		this.customerID = Integer.parseInt(data[2]);
		this.email = data[3];
		this.streetNumber = Integer.parseInt(data[4]);
		this.streetName = data[5];
		this.apptNum = data[6];
		this.city = data[7];
		this.state = data[8];
		this.country = data[9];
		this.zip = Integer.parseInt(data[10]);
	}

	public String getFnameName() {
		return fname;
	}

	public void setFnameName(String name) {
		this.fname = name;
	}

	public String getLnameName() {
		return lname;
	}

	public void setLnameName(String name) {
		this.lname = name;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
