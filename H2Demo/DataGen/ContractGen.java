import java.util.ArrayList;
import java.util.Random;

public class ContractGen {

    ArrayList<Integer> paymentID;
    ArrayList <String> billDate;
    ArrayList <Integer> totalPackageNum;

    PaymentGen payment;

    public ContractGen(PaymentGen payment){
        this.payment = payment;

    }

    public void init(){
        ArrayList<String> paymentTypesList = payment.type;
        ArrayList<Integer> payIDList = payment.paymentID;
        fillPaymentID(paymentTypesList, payIDList);
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
                totalPackageNum.add(count);
                billDate.add(rand.nextInt(12)+1 + "/1");
            }
        }
    }
}
