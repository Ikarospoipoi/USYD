package A1;

public class CheckBalance extends Transaction {

    /** 
     * Constructor for a CheckBalance transaction.
    */
    public CheckBalance(int transactionNum, Customer user, ATM atm) {
        // Calls the super class Transaction's constructor method
        super(transactionNum, user, TransactionType.CHECKBALANCE, atm);
    }

    /** 
     * Carries out the check balance transaction.
    */
    // Main function to deposit that can be called in main, includes other functions
    public void doTransaction() {
        // Contains all logic for deposit transaction
        System.out.println(String.format("Your account balance is: $%.2f", this.getCustomer().getCardBalance()));

        // At end, add transaction to database
        this.addToDatabase(0, getCustomer().getCardBalance());
    }
}