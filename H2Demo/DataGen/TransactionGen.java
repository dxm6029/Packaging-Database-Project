import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

public class TransactionGen {
    ArrayList<Integer> transactionID;
    ArrayList<Double> cost;
    ArrayList<String> rfName;
    ArrayList<String> lrName;
    ArrayList<Integer> streetNum;
    ArrayList<String> streetName;
    ArrayList<String> apptNum;
    ArrayList<String> city;
    ArrayList<String> state;
    ArrayList<String> country;
    ArrayList<Integer> zip;
    Faker faker = new Faker();
    public void init(){
        createNames();
        createCost();
        createID();
        createAddress();
    }

    private void createCost() {
        for(int i = 0; i < 750; i++){
            cost.add(faker.number().randomDouble(2,3, 20));
        }
    }

    private void createAddress() {
        Faker usFaker = new Faker(new Locale("en-us"));
        for(int i = 0; i < 700; i++){
            streetNum.add(Integer.getInteger(usFaker.address().buildingNumber()));
            streetName.add(usFaker.address().streetAddress());
            apptNum.add(usFaker.address().buildingNumber());
            city.add(usFaker.address().city());
            state.add(usFaker.address().state());
            country.add(usFaker.address().country());
            zip.add(Integer.getInteger(usFaker.address().zipCode()));
        }
        for(int i = 0; i < 50; i++){
            streetNum.add(Integer.getInteger(faker.address().buildingNumber()));
            streetName.add(faker.address().streetAddress());
            apptNum.add(faker.address().buildingNumber());
            city.add(faker.address().city());
            state.add(faker.address().state());
            country.add(faker.address().country());
            zip.add(Integer.getInteger(faker.address().zipCode()));
        }
    }

    private void createID() {
        for(int i = 0; i < 750; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(transactionID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
        }
    }

    private void createNames() {
        for(int i = 0; i < 750; i++){
            rfName.add(faker.name().firstName());
            lrName.add(faker.name().lastName());
        }
    }
}
