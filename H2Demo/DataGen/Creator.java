import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Creator {
    public static void main(String[] args) {
        CustomerGen customer = new CustomerGen();
        customer.init();

        TransactionGen transaction = new TransactionGen();
        transaction.init();

        PaymentGen payment = new PaymentGen();
        payment.init();

        MakesTransactionGen makesTransaction = new MakesTransactionGen(customer,transaction, payment);
        makesTransaction.init();

        PostalWorkerGen worker = new PostalWorkerGen();
        worker.init();

        PackagesGen packages = new PackagesGen(transaction, worker);
        packages.init();

        TransportationGen transportation = new TransportationGen();
        transportation.init();

        PackageTransportationGen packageTransportation = new PackageTransportationGen(packages, transportation);
        packageTransportation.init();

        customerCSV(customer);
    }

    private static void customerCSV(CustomerGen customer) {
        String output = "";
        for(int i = 0; i < 500; i++) {
            output += customer.fname.get(i) + ",";
            output += customer.lname.get(i) + ",";
            output += customer.customerID.get(i) + ",";
            output += customer.email.get(i) + ",";
            output += customer.streetNumber.get(i) + ",";
            output += customer.streetName.get(i) + ",";
            output += customer.apptNum.get(i) + ",";
            output += customer.city.get(i) + ",";
            output += customer.state.get(i) + ",";
            output += customer.country.get(i) + ",";
            output += customer.zip.get(i) + "\n";
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Customer.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print(output);
        writer.close();

    }

}
