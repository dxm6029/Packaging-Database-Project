public class Creator {
    public static void main(String[] args) {
        CustomerGen customer = new CustomerGen();
        customer.init();
        TransactionGen transaction = new TransactionGen();
        transaction.init();
        MakesTransactionGen makesTransaction = new MakesTransactionGen(customer,transaction);
        makesTransaction.init();


    }
}
