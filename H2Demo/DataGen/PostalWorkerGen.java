import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

public class PostalWorkerGen {

    ArrayList<String> username;
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
        String usernamegen = faker.name().firstName() + "_" + faker.name().lastName();
        while(username.contains(usernamegen)){
            usernamegen = faker.name().firstName() + "_" + faker.name().lastName();
        }
        username.add(usernamegen);
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
