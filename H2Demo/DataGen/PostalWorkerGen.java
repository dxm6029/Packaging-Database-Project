import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

public class PostalWorkerGen {

    ArrayList<String> name;
    ArrayList<String> location;
    ArrayList<Integer> workerID;

    Faker faker = new Faker();
    Faker usFaker = new Faker(new Locale("en-us"));

    public void init(){
        createName();
        createLocation();
        createID();
    }

    private void createName() {
        name.add(faker.funnyName().name());
    }

    private void createLocation() {
        String locationgen = usFaker.address().city() + ", " + usFaker.address().state();
        while(location.contains(locationgen)){
            locationgen = usFaker.address().city() + ", " + usFaker.address().state();
        }
        location.add(locationgen);
    }

    private void createID() {
        for(int i = 0; i < 500; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(workerID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
            workerID.add(id);
        }
    }
}
