import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

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
    PostalWorkerGen worker;

    ArrayList<String> locationList = worker.location;
    ArrayList<Integer> transID = transaction.transactionID;

    public PackagesGen(TransactionGen transaction, PostalWorkerGen worker){
        this.transaction = transaction;
        this.worker = worker;
    }
    public void init(){
        createID();
        createLocation();
        createTransID();
        createWeight();
    }

    private void createWeight() {
        for(int i = 0; i < 750; i++){
            weight.add(faker.number().randomDouble(2,1,500));
        }
    }

    private void createTransID() {
        transactionID = transID;
    }

    private void createLocation() {
        Random rand = new Random();
        for (int i = 0; i < 750; i++){
            location.add(locationList.get(rand.nextInt(locationList.size()))); // random numbers between 0 and 10
        }
    }

    private void createID() {
        for(int i = 0; i < 750; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(packageID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
            packageID.add(id);
        }
    }
}
