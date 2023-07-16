package A1;

public abstract class Transaction {
    protected int transactionNum;
    protected TransactionType type;
    protected Customer customer;
    protected ATM atm;

    /** 
     * Constructor for a Transaction.
    */
    public Transaction(int transactionNum, Customer customer, TransactionType type, ATM atm) {
        this.transactionNum = transactionNum; // Given from ATM.getNextTransactionNum()
        this.type = type;
        this.customer = customer;
        this.atm = atm;
    }

    /** 
     * Returns if the given amount may be divided into cash.
     * Given a boolean specifying whether the amount should be just in notes or if notes and cash are accepted.
    */
    public static boolean isCashDivisable(double cashAmount, boolean isDeposit) {
        // check if customer is inputting negative amount
        if(isDeposit == false) {//If is not deposit, then can withdraw coins
            if (cashAmount < 0) {
                return false;
            }

            double X100 = cashAmount * 100;
            if (X100 % 5 == 0) {
                return true;
            }
            return false;
        }else{//If is deposit, then only deposit notes
            if (cashAmount < 0) {
                return false;
            }

            if (cashAmount % 5 == 0) {
                return true;
            }
            return false;
        }
    }
    
    /** 
     * Carry out a transaction using the given ATM.
    */
    // Main logic for each of the 3 transaction types - implemented in each subclass
    // May need a return type?
    public abstract void doTransaction();

    /** 
     * Returns the number of the transaction.
    */
    public int getTransactionNum() {
        return this.transactionNum;
    }

    /** 
     * Returns the type of the transaction.
    */
    public TransactionType getTransactionType() {
        return this.type;
    }

    /** 
     * Returns the customer carrying out the transaction.
    */
    public Customer getCustomer() {
        return this.customer;
    }

    /** 
     * Makes a new SQL entry for this transaction.
     * alteredAmount: positive amount withdrawn/deposited, make anything for balance
     * newAccBalance: new account balance of customer
    */
    public void addToDatabase(double alteredAmount, double newAccBalance) {
        Crud.updateTransaction(this.transactionNum, this.type, alteredAmount, newAccBalance, this.atm.getATMid());
    }
}