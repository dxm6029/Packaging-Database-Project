import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

public class TransportationGen {

    ArrayList<Integer> transportID;
    ArrayList<String> company;
    ArrayList <Integer> driverID;

    Faker faker = new Faker();

    public void init(){
       createTransportID();
       createCompany();
       createDriverID();
    }

    public void createTransportID(){
        for(int i = 0; i < 10; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(transportID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
        }
    }

    public void createCompany(){
        String [] companies = {"UPS", "Fed Ex", "Unites States Postal Service", "DHL"};
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            company.add(companies[rand.nextInt(4)]); // random numbers between 0 and 3
        }

    }

    public void createDriverID(){
        for(int i = 0; i < 10; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(driverID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
            driverID.add(id);
        }
    }

}
