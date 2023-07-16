package A1;

import java.util.*;
public class ATM {
    private String atmId;
    private double balance;
    private int nextTransactionNum;

    public ATM(String atmId) {
        this.atmId = atmId;
        this.balance = Crud.getATMBalance(atmId);
        if(Crud.rowsinTransactionTable() == 0){
            this.nextTransactionNum = 1;
        }
        else{
            this.nextTransactionNum = Crud.rowsinTransactionTable() + 1;
        }
    }

    public String getATMid() {
        return this.atmId;
    }

    public double getATMBalance() {
        return this.balance;
    }

    /**
     * Returns the ATM's next transaction number and updates this attribute in the ATM.
    */
    public int getNextTransactionNum() {
        int oldNum = this.nextTransactionNum; // Save old value to return
        this.nextTransactionNum++; // Increment next transaction number (assumes transaction number definitely gets used)
        return oldNum; //should this not return the incremented tractionNUm? no - the attribute is the next number to be used so this assumes when it's retrieved (only to directly set the transaction number of a Transaction) it's used. So it increments ready for the next time it is used.
    }

    /**
     * Returns the ATM's next transaction number and updates this attribute in the ATM.
     * amount may be negative or positive.
    */
    public void updateATMBalance(double amount) {
        this.balance += amount;
        Crud.updateATMBalance(this.atmId, this.balance);
    }

    /**
     * Asks for the user's card number and returns it.
    */
    public String userCardNum() {
        // Take Card Number From User input
        Scanner CardNumInput = new Scanner(System.in);
        System.out.println("\n========= CARD NUMBER =========");
        System.out.println(">> ENTER 5 DIGIT CARD NUMBER");
        return CardNumInput.nextLine();
    }

    /**
     * Checks if the given card number is valid.
     * Returns the corresponding Customer if valid and null if invalid.
    */
    public Customer initCustomer(String cardNum) {
        // Check card exists
        int exists = Crud.cardExists(cardNum);
        if (exists != 1) {
            return null;
        }

        // Check card is used after its start date and is used before its expiry date (compare with current date)
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int date = (year * 12) + month;

        String start_date = Crud.getCardStartDate(cardNum);
        String sub_start_year = start_date.substring(2);
        String sub_start_month = start_date.substring(0, 2);
        int start_date_int = ((2000+Integer.valueOf(sub_start_year)) * 12) + Integer.valueOf(sub_start_month);

        String expiry_date = Crud.getCardExpiryDate(cardNum);
        String sub_expiry_year = expiry_date.substring(2);
        String sub_expiry_month = expiry_date.substring(0, 2);
        int expir_date_int = ((2000+Integer.valueOf(sub_expiry_year)) * 12) + Integer.valueOf(sub_expiry_month);

        if (start_date_int > date){
            System.out.println("You are attempting to use this card before its start date.");
            return null;
        }
        if (expir_date_int < date){
            System.out.println("You are attempting to use this card after it has expired.");
            return null;
        }
        // Create Customer object
        Customer customer = new Customer(cardNum);
        // Check card is not lost or stolen
        if (customer.getCardStatus() == CardStatus.LOST || customer.getCardStatus() == CardStatus.STOLEN) {
            System.out.println("This card has been detected as reported as lost or stolen. This card has been confiscated.");
            System.out.println("We are sorry for any inconvenience. Please contact your nearest XYZ Bank branch.");
            return null;
        }
        // Card is valid so return the newly created Customer object
        return customer;
    }

    /*
    Validate Pin contains 3 features:
    1. Take input(PIN) from user.
    2. No more than three attempts.(Exit program if more than 3 attempts. Will take 3 inputs in total.)
    3. Validate the input with the PIN from customer object.()
     */
    public boolean validatePIN(Customer new_customer){
        System.out.println("\n============= PIN =============");
        Scanner pinInput = new Scanner(System.in);  // Create a Scanner object
        int attempt = 1; // First attempt number

        while(attempt <=3 ){

            // Display different message while making different attempt.
            switch (attempt){
                // First attempt
                case 1:
                    System.out.println("Please enter your PIN. After 3 incorrect attempts your card will be blocked.");
                    break;
                // Second attempt
                case 2:
                    System.out.println("Incorrect PIN given. Please enter your PIN again.");
                    break;
                // Third attempt
                case 3:
                    System.out.println("Incorrect PIN given. Please enter your PIN again. Reminder: If this attempt fails, your card will be blocked.");
                    break;
            }
            System.out.printf("Attempts left: %d%n", 4 - attempt);
            System.out.println(">> ENTER 4 DIGIT PIN");

            /*
            Get PIN from user input
             */
            //Read user input
            String pin = pinInput.nextLine();

            // Check if a match, match return true, if not match return false.
            boolean match = new_customer.checkPIN(pin);

            //Validation
            //Pin matched, break the loop.
            if(match){
                System.out.println("Your PIN matches and your card is valid.");
                return true;
            }

            //while loop count
            attempt++;
        }
        // If more than three attempts, Block card and Exit.
        // Block card
        new_customer.blockCard();
        System.out.println("Sorry, you have exceeded your 3 attempts to enter your PIN. Your card has been blocked.\nPlease contact your nearest XYZ Bank branch.");
        return false;
    }

    /*
    Allows the admin to perform routine maintenance.
    Returns true if cash was added, otherwise returns false.
     */
    public boolean runAdminSystem(){
    //Admin full system,no output.
        Scanner scan = new Scanner(System.in);// Create scanner obj
        System.out.println("\n============ ADMIN ============");
        System.out.println("\nYou have entered the Admin System.\n"); // Welcome message(Only appear once)
        // Ask for how much cash to be added to the ATM
        System.out.println("Routine maintenance: How much cash are you adding to the system?");

        //Loop until admin put only note into the ATM
        int counter = 0;
        double cashAdded = -1;
        do {
            if (counter > 0) {
                //Print error message
                System.out.println("Please enter a valid AUD cash amount.");
            }
            // Print message
            System.out.println(">> ENTER CASH AMOUNT");
            System.out.println("** Press 0 to CANCEL and eject your card **");
            // Get input
            cashAdded = scan.nextDouble();
            if(cashAdded == 0){
                System.out.println("No cash was added.");
                ejectCard("AC"); //Admin cancel
                return false;
            }
            counter++;
        } while (!Transaction.isCashDivisable(cashAdded, false));
        // If in correct cash, add to ATM
        this.updateATMBalance(cashAdded);
        //Exit
        System.out.printf("Routine maintenance completed.\n$%.2f was added to this ATM.%n", cashAdded);
        ejectCard("XX"); // general eject message
        return true;
    }

    /*
    Standard System
     */

    //Method to display options
    public void displayOptions(){
        //display all the options for transactions
        System.out.println("\nPlease select one of the options below:");
        System.out.println(">> WITHDRAW from your account      type 1");
        System.out.println(">> DEPOSIT into your account       type 2");
        System.out.println(">> CHECK BALANCE of your account   type 3");
        System.out.println(">> CANCEL and eject your card      type 0");
    }

    /**
     * Allows the user to choose a transaction: withdraw, deposit, check balance or cancel.
     * Returns true if withdraw or deposit was completed, false if cancel selected.
    */
    public boolean runStandardSystem(Customer new_customer){
        //Welcome Message
        System.out.println("\n====== CHOOSE TRANSACTION =====");
        /*
        /   Process Transactions
        */
        Scanner tran_scan = new Scanner(System.in);
        // Ask for a command until they give a correct one
        while (true) {
            this.displayOptions();
            // Changed to entering numbers
            int tran_type = tran_scan.nextInt();
            if (tran_type == 1) {
                System.out.println("\n=========== WITHDRAW ==========");
                // do withdraw here
                Withdraw withdraw = new Withdraw(this.getNextTransactionNum(), new_customer, this);
                withdraw.doTransaction(); // complete function
                ejectCard("WD");
                return true;
            }
            else if (tran_type == 2) {
                System.out.println("\n=========== DEPOSIT ===========");
                // do deposit here
                Deposit deposit = new Deposit(this.getNextTransactionNum(), new_customer, this);
                deposit.doTransaction(); // complete function
                ejectCard("WD");
                return true;
            }
            else if (tran_type == 3) {
                System.out.println("======== CHECK BALANCE ========");
                //do check balance here
                CheckBalance checkBalance = new CheckBalance(this.getNextTransactionNum(), new_customer, this);
                checkBalance.doTransaction(); // complete function
            }
            else if (tran_type == 0) {
                // Cancel
                ejectCard("CL");
                return false;
            }

        }//while loop end

    }

    /**
     * Ejects the user's card with a specific message
    */
    public void ejectCard(String transType) {
        switch (transType){
            case "AC":  // Admin Cancel
                System.out.println("Cancelling routine maintenance...");
                break;
            case "WD":  // Withdraw and Deposit
                System.out.println("Transaction completed. Thank you for choosing XYZ bank.");
                break;
            case "CL": // Standard user cancel
                System.out.println("Cancelling...");
                break;
        }
        System.out.println("Your card has been ejected. Have a nice day.");
        System.out.println("============ X Y Z ============");
        // System.exit(0);//Exit program
    }
}
