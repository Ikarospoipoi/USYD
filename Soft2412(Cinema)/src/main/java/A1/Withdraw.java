package A1;

import java.util.Scanner;

public class Withdraw extends Transaction {
    private float amount;

    /**
     * Constructor for a Withdraw transaction.
     */
    public Withdraw(int transactionNum, Customer customer, ATM atm) {
        super(transactionNum, customer, TransactionType.WITHDRAW, atm);
    }

    /**
     * Carries out the Withdraw transaction.
     */
    // Main function to withdraw that can be called in main, includes other functions
    public void doTransaction() {
        // Contains all logic for withdraw transaction

        //check if customer is inputting numerically
        float check = this.inputUserAmount(this.getCustomer());
        if(check == -1){
            return;
        }

        while(check == -2){
            check = this.inputUserAmount(this.getCustomer());
        }


        // check if the withdrawal amount is supportable
        while(isCashDivisable(Double.valueOf(this.amount), false) == false){
            System.out.println("Please enter a valid AUD cash amount.\n");
            if(this.inputUserAmount(this.getCustomer()) == -1){
                return;
            }

        }

        if(this.displayWithdrawDenied()){
            return;
        }
        // At end, add transaction to database
        else{
            this.addToDatabase(amount, this.getCustomer().getCardBalance() - amount);
            this.getCustomer().updateBalance(this.getCustomer().getCardBalance() - amount, this.type);
            this.displayDispenseCash();
            this.printReceipt();
        }
    }

    /**
     * Print out receipt
     */
    public int printReceipt() {
        System.out.println("||      XYZ Bank - RECEIPT");
        System.out.println("||  Transaction ID:     " + String.valueOf(this.transactionNum));
        System.out.println("||  Transaction type:   " + this.type);
        System.out.println("||  Withdrawal amount:  " + String.valueOf(this.amount));
        System.out.println("||  Account balance:    " + String.valueOf(this.getCustomer().getCardBalance()));
        return -1;
    }

    /**
     * Receives an amount from the customer.
     */
    // Pass in customer making the transaction and the ATM being used
    public float inputUserAmount(Customer user) {
        // Display and get amount
        System.out.println("You may withdraw AUD notes and coins.");
        System.out.println(">> ENTER AMOUNT TO WITHDRAW");
        System.out.println("** Press 0 to CANCEL deposit and go back to the menu **");
        Scanner withdraw_input = new Scanner(System.in);
        String amount_String = withdraw_input.nextLine();
        if (amount_String.equals("0")){
            System.out.println("CANCEL: Returning to the menu...");
            atm.runStandardSystem(customer);
            return -1;
        }
        try{
            amount = Float.parseFloat(amount_String);
            return this.amount;
        }
        catch (Exception e){
            System.out.println("Amount must be numerical.\n");

        }
        // Return amount user entered
        return -2;
    }

    /**
     * Displays cash is dispensed to the customer from their account via the ATM.
     */
    public int displayDispenseCash() {
        // Display message
        System.out.println(String.format("Success! You have withdrawn $%.2f from your account.", this.amount));
        return -1;
    }

    /**
     * Displays to the customer that the withdraw was denied and gives the reason.
     */
    public boolean displayWithdrawDenied() {
        // Display reason why it was denied
        float card_balance = this.getCustomer().getCardBalance();
        float ATM_balance = (float) this.atm.getATMBalance();

        if(card_balance < this.amount){
            System.out.println("Withdraw transaction denied.\n");
            System.out.println("You have an insufficient account balance.");
            return true;
        }
        if(ATM_balance < this.amount){
            System.out.println("Withdraw transaction denied.\n");
            System.out.println("Sorry, this ATM has insufficient cash available. Please contact an administrator at your nearest XYZ Bank branch.");
            System.out.println("We apologise for any inconvenience.");
            return true;
        }
        return false;

    }


}