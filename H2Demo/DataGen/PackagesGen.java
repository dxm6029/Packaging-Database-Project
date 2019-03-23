import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

public class PackagesGen {
    ArrayList<String> packageType;
    ArrayList<Double> weight;
    ArrayList<String> deliveryType;
    ArrayList<Integer> packageID;
    ArrayList<String> location;
    ArrayList<String> expectedDelivery;
    ArrayList<String> extraInfo;
    ArrayList<String> deliveryTime;
    ArrayList<Integer> transactionID;

    Faker faker = new Faker();
    Faker usFaker = new Faker(new Locale("en-us"));

    TransactionGen transaction;

    public PackagesGen(TransactionGen transaction){
        this.transaction = transaction;
    }
    public void init(){
        createID();
        createLocation();
    }

    private void createLocation() {
        for(int i = 0; i < 500; i++){

        }
    }

    private void createID() {
        for(int i = 0; i < 500; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(packageID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
            packageID.add(id);
        }
    }
}
