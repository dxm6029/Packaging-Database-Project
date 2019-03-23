import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PackagesGen {
    ArrayList<String> packageType;
    ArrayList<Double> weight;
    ArrayList<String> deliveryType;
    ArrayList<Integer> packageID;
    ArrayList<String> location;
    ArrayList<String> startedDelivery;
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
        createPackageType();
        createDeliveryType();
        createExtraInfo();
        createStartedDelivery();
        createDeliveryTime();
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

    public void createPackageType(){
        String [] packType = {"Envelope", "Post Card", "Large", "Medium", "Small", "Extra Large", "Extra Small"};
        Random rand = new Random();
        for (int i = 0; i < 750; i++){
            packageType.add(packType[rand.nextInt(7)]);
        }
    }

    public void createDeliveryType(){
        String [] delivType = {"1-Day", "Overnight", "3-5 Day", "7+ Day"};
        Random rand = new Random();
        for (int i = 0; i < 750; i++){
            deliveryType.add(delivType[rand.nextInt(4)]); // random numbers between 0 and 3
        }
    }

    public void createExtraInfo(){
        String [] exInfo = {"1-Day", "Overnight", "3-5 Day", "7+ Day"};
        Random rand = new Random();
        for (int i = 0; i < 750; i++){
            extraInfo.add(exInfo[rand.nextInt(4)]); // random numbers between 0 and 3
        }
    }

    public void createStartedDelivery(){
        for (int i = 0; i < 750; i++)
            startedDelivery.add(faker.date().past(0, TimeUnit.HOURS).toString());
    }

    public void createDeliveryTime(){
        for (int i = 0; i < 750; i++)
            deliveryTime.add(null);
    }
}
