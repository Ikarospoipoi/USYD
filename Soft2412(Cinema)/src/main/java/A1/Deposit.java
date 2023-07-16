package A1;

import java.util.Scanner;

public class Deposit extends Transaction {

    private float amount;

    /**
     * Constructor for a Deposit transaction.
     */
    public Deposit(int transactionNum, Customer user, ATM atm) {
        super(transactionNum, user, TransactionType.DEPOSIT, atm);
    }

    /**
     * Carries out the deposit transaction.
     */
    public void doTransaction() {
        // Contains all logic for deposit transaction
        amount = inputUserAmount();

        displayDepositCash();

        atm.updateATMBalance(amount);

        // At end, add transaction to database
        addToDatabase(amount, getCustomer().getCardBalance() + amount);
        this.getCustomer().updateBalance(this.getCustomer().getCardBalance() + amount, this.type);
        // Print receipt
        this.printReceipt();
        return;
    }

    /**
     * Print outs receipt
     */
    public int printReceipt() {
        System.out.println("||      XYZ Bank - RECEIPT");
        System.out.println("||  Transaction ID:     " + String.valueOf(this.transactionNum));
        System.out.println("||  Transaction type:   " + this.type);
        System.out.println("||  Deposit amount:     " + String.valueOf(this.amount));
        System.out.println("||  Account balance:    " + String.valueOf(this.getCustomer().getCardBalance()));
        return -1;
    }

    /**
     * Receives an amount from the customer.
     */
    public float inputUserAmount() {
        Scanner pinInput = new Scanner(System.in);  //Create a Scanner object
        System.out.println("You may only deposit AUD notes. Coins are not accepted.");
        System.out.println(">> ENTER AMOUNT TO DEPOSIT");
        System.out.println("** Press 0 to CANCEL deposit and go back to the menu **");
        while(true){
            String amount_String = pinInput.nextLine();
            // Cancel
            if (amount_String.equals("0")){
                System.out.println("CANCEL: Returning to the menu...");
                atm.runStandardSystem(customer);
                return 0;
            }

            try{
                amount = Float.parseFloat(amount_String);
                if(isCashDivisable(amount,true) == false){
                    System.out.println("You may only deposit AUD notes. Coins are not accepted.");
                    System.out.println("Please enter again:");
                }else{
                    return amount;
                }
            }catch (Exception e) {
                System.out.println("You may only deposit AUD notes. Coins are not accepted.");
                System.out.println("Please enter again:");
            }
        }
    }

    /**
     * Displays cash is deposited from the customer to their account via the ATM.
     */
    public int displayDepositCash() {
        // Display message
        System.out.println(String.format("Success! You have deposited $%.2f into your account.", this.amount));
        return 1;
    }

}