import com.github.javafaker.Faker;

import java.util.ArrayList;

public class PaymentGen {

    TransactionGen transaction;
    ArrayList<Integer> paymentID;
    ArrayList<Double> cost;
    ArrayList<String> type;

    Faker faker = new Faker();

    public PaymentGen(TransactionGen transaction){
        this.transaction = transaction;
    }

    public void init(){
        ArrayList<Integer> transactionIDList = transaction.transactionID;

    }
}
