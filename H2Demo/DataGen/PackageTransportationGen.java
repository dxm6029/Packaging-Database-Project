import java.util.ArrayList;
import java.util.Random;

public class PackageTransportationGen {

    ArrayList<Integer> packageID;
    ArrayList<Integer> transportID;

    PackagesGen packages;
    TransportationGen transportation;

    public PackageTransportationGen(PackagesGen packages, TransportationGen transportation){
        this.packages = packages;
        this.transportation = transportation;
    }

    public void init(){
        ArrayList<Integer> packageIDList = packages.packageID;
        ArrayList<Integer> transportationIDList = transportation.transportID;
        fillData(packageIDList, transportationIDList);
    }

    private void fillData(ArrayList<Integer> packageIDList, ArrayList<Integer> transportationIDList) {
        packageID = packageIDList;
        Random rand = new Random();
        for(int i = 0; i < 500; i++){
            transportID.add(transportationIDList.get(rand.nextInt(transportationIDList.size())));
        }
    }

}
