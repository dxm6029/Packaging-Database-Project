import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

public class PaymentGen {

    ArrayList<Integer> paymentID;
    ArrayList<String> type;

    Faker faker = new Faker();

    public void init(){
        createPaymentID();
        createType();
    }

    public void createPaymentID(){
        for(int i = 0; i < 750; i++){
            Integer id = Integer.getInteger(faker.idNumber().valid());
            while(paymentID.contains(id)){
                id = Integer.getInteger(faker.idNumber().valid());
            }
            paymentID.add(id);
        }
    }

    public void createType(){
        String [] paymentType = {"Contract", "Credit Card", "Prepaid"};
        Random rand = new Random();
        for (int i = 0; i < 750; i++){
            type.add(paymentType[rand.nextInt(3)]); // random numbers between 0 and 3
        }
    }
}
