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

    }
}
