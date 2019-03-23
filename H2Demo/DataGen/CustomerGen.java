import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

public class CustomerGen {
    ArrayList<String> fname;
    ArrayList<String> lname;
    ArrayList<Integer> customerID;
    ArrayList<String> email;
    ArrayList<Integer> streetNumber;
    ArrayList<String> streetName;
    ArrayList<String> apptNum;
    ArrayList<String> city;
    ArrayList<String> state;
    ArrayList<String> country;
    ArrayList<Integer> zip;
    Faker faker = new Faker();

    public void init(){
        createNames();
        createID();
        createEmail();
        createAddress();
    }

    private void createAddress() {
        Faker usFaker = new Faker(new Locale("en-us"));
        for(int i = 0; i < 450; i++){
            streetNumber.add(Integer.getInteger(usFaker.address().buildingNumber()));
            streetName.add(usFaker.address().streetAddress());
            apptNum.add(usFaker.address().buildingNumber());
            city.add(usFaker.address().city());
            state.add(usFaker.address().state());
            country.add(usFaker.address().country());
            zip.add(Integer.getInteger(usFaker.address().zipCode()));
        }
        for(int i = 0; i < 50; i++){
            streetNumber.add(Integer.getInteger(faker.address().buildingNumber()));
            streetName.add(faker.address().streetAddress());
            apptNum.add(faker.address().buildingNumber());
            city.add(faker.address().city());
            state.add(faker.address().state());
            country.add(faker.address().country());
            zip.add(Integer.getInteger(faker.address().zipCode()));
        }
    }

    private void createEmail() {
        for(int i = 0; i < 500; i++){
            String emailgen = faker.internet().emailAddress();
            while(email.contains(emailgen)) {
                emailgen = faker.internet().emailAddress();
            }
            email.add(emailgen);
        }
    }

    private void createID() {
        for(int i = 0; i < 500; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(customerID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
            customerID.add(id);
        }
    }

    private void createNames() {
        for(int i = 0; i < 500; i++){
            fname.add(faker.name().firstName());
            lname.add(faker.name().lastName());
        }
    }
}
