import java.util.ArrayList;

public class PrepaidGen {

    ArrayList<Integer> paymentID;
    ArrayList <Boolean> used;

    PaymentGen payment;

    public PrepaidGen(PaymentGen payment){
        this.payment = payment;

    }

    public void init(){
        ArrayList<String> paymentTypesList = payment.type;
        ArrayList<Integer> payIDList = payment.paymentID;
        fillPaymentID(paymentTypesList, payIDList);
    }

    public void fillPaymentID(ArrayList<String> paymentTypeList, ArrayList<Integer> payIDList){
        for (int i = 0; i < 750; i++){
            if (paymentTypeList.get(i).equals("Prepaid")){
                paymentID.add(payIDList.get(i));
                used.add(true);
            }
        }
    }

}
