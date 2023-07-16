package A1;

public class Customer {
    private String cardNum;
    private CardStatus cardStatus;
    private boolean admin;
    private float cardBalance;
    private String pin;
    private String startDate;
    private String expDate;

    /**
     * Constructor for a Customer.
     */
    public Customer(String cardNum) {
        this.cardNum = cardNum;
        this.cardStatus = Crud.getCardStatus(cardNum);
        this.admin = Crud.isCardAdmin(cardNum);
        this.cardBalance = Crud.availableFunds(cardNum);
        this.pin = Crud.getCardPin(cardNum);
        this.startDate = Crud.getCardStartDate(cardNum);
        this.expDate = Crud.getCardExpiryDate(cardNum);
    }

    /**
     * Returns card number of customer.
     */
    public String getCardNum() { return this.cardNum; }

    /**
     * Returns the card status of customer (either active, lost, stolen or blocked)
     */
    public CardStatus getCardStatus() { return this.cardStatus; }

    /**
     * Returns if the customer is an admin or not.
     */
    public boolean isAdmin() { return this.admin; }

    /**
     * Returns the current card balance of the customer.
     */
    public float getCardBalance() { return this.cardBalance; }

    /**
     * Returns PIN of the customer.
     */
    public String getPIN() { return this.pin; }

    /**
     * Returns start date of the customer's card.
     */
    public String getStartDate() { return this.startDate; }

    /**
     * Returns expiry date of the customer's card.
     */
    public String getExpDate() { return this.expDate; }

    /**
     * Returns whether the given PIN matches that of the customer.
     */
    public boolean checkPIN(String inputPIN) { return inputPIN.equals(this.pin); }

    /**
     * Blocks the customer's card and updates the data base.
     */
    public void blockCard() {
        // Update database to blocked card status
        Crud.updateCardStatus(getCardNum(), "Blocked");
        this.cardStatus = CardStatus.BLOCKED;
        return;
    }

    /**
     * Updates the current balance of the customer in the database.
     */
    public void updateBalance(float amount, TransactionType type) {
        this.cardBalance = amount;
        // Update database...
        Crud.updateCustomer(this.cardNum, type, amount);
    }
}
