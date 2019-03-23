public class Creator {
    public static void main(String[] args) {
        CustomerGen customer = new CustomerGen();
        customer.init();
        TransactionGen transaction = new TransactionGen();
        transaction.init();
        MakesTransactionGen makesTransaction = new MakesTransactionGen(customer,transaction);
        makesTransaction.init();
        PostalWorkerGen worker = new PostalWorkerGen();
        worker.init();
        PackagesGen packages = new PackagesGen(transaction);
        packages.init();
        TransportationGen transportation = new TransportationGen();
        transportation.init();
        PaymentGen payment = new PaymentGen(transaction);
        payment.init();


    }
}
