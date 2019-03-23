import java.util.ArrayList;
import java.util.Random;

public class CreditCardGen {

    ArrayList <Integer> paymentID;
    ArrayList <String> cardHolderName;
    ArrayList <Integer> cardNum;
    ArrayList <Integer> cvv;
    ArrayList <String> expirationDate;

    PaymentGen payment;
    MakesTransactionGen transactions;


    public CreditCardGen(PaymentGen payment){
        this.payment = payment;
    }

    public void init(){
        ArrayList<Integer> payIDList = payment.paymentID;
//        ArrayList<Integer>
//        ArrayList<String> cardHolderNameList = transactions.;
//        fillPaymentID(paymentTypesList, payIDList);
    }

    public void fillPaymentID(ArrayList<String> paymentTypeList, ArrayList<Integer> payIDList){
        Random rand = new Random();
        for (int i = 0; i < 750; i++){
            if (paymentTypeList.get(i).equals("Contract")){
                int id = payIDList.get(i);
                int count = 0;
                for (int j = 0; j < 750; j++){
                    if (payIDList.get(i) == id && paymentTypeList.get(i).equals("Contract")){
                        count++;
                    }
                }
                paymentID.add(id);
//                totalPackageNum.add(count);
//                billDate.add(rand.nextInt(12)+1 + "/1");
            }
        }
    }
}
