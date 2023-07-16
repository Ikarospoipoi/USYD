package A1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ATMTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreOutStream() {
        System.setOut(originalOut);
    }

    /** 
     * Constructor/getter methods tests
     *   Normal (positive non-zero balance, existing transactions)
     *   Zero balance
    */
    @Test
    public void constructorNormal() {
        ATM testATM = new ATM("2");
        assertEquals(788, testATM.getATMBalance(), "ATM has incorrect balance");
        assertEquals(Crud.lastTransactionNumber("2") + 1, testATM.getNextTransactionNum(), "ATM has incorrect next transaction number");
    }

    @Test
    public void constructorZeroBalance() {
        ATM testATM = new ATM("1");
        assertEquals(0, testATM.getATMBalance(), "ATM should have balance of 0");
    }

    /** 
     * updateATMBalance(double amount) test
     *   Positive integer amount
     *   Positive fraction amount
     *   Negative integer amount
     *   Negative fraction amount
     *   Zero amount
    */
    @Test
    public void posIntUpdateBalance() {
        ATM testATM = new ATM("2");
        double beforeBal = testATM.getATMBalance();
        testATM.updateATMBalance(257);
        assertEquals(beforeBal + 257, testATM.getATMBalance(), "ATM should increase balance");
        testATM.updateATMBalance(-257); // undo in database
    }

    @Test
    public void posFractionUpdateBalance() {
        ATM testATM = new ATM("2");
        double beforeBal = testATM.getATMBalance();
        testATM.updateATMBalance(6801.75);
        assertEquals(beforeBal + 6801.75, testATM.getATMBalance(), "ATM should increase balance");
        testATM.updateATMBalance(-6801.75); // undo in database
    }

    @Test
    public void negIntUpdateBalance() {
        ATM testATM = new ATM("2");
        double beforeBal = testATM.getATMBalance();
        testATM.updateATMBalance(-1);
        assertEquals(beforeBal - 1, testATM.getATMBalance(), "ATM should lower balance");
        testATM.updateATMBalance(1); // undo in database
    }

    @Test
    public void negFractionUpdateBalance() {
        ATM testATM = new ATM("2");
        double beforeBal = testATM.getATMBalance();
        testATM.updateATMBalance(-574.05);
        assertEquals(beforeBal - 574.05, testATM.getATMBalance(), "ATM should lower balance");
        testATM.updateATMBalance(574.05); // undo in database
    }

    @Test
    public void zeroUpdateBalance() {
        ATM testATM = new ATM("2");
        double beforeBal = testATM.getATMBalance();
        testATM.updateATMBalance(0);
        assertEquals(beforeBal, testATM.getATMBalance(), "ATM keep balance the same");
    }

    /** 
     * initCustomer() test - doesn't alter database
     *   Card doesn't exist
     *   Card given is garbage string (doesn't exist)
     *   Card number too short
     *   Card number too long
     *   Card number empty
     *   Card start date is after the current date
     *   Card expiry date is before the current date
     *   Card is lost - check if blocked
     *   Card is stolen - check if blocked
     *   Customer is valid standard user
     *   Customer is valid admin
    */
    @Test
    public void nonExistentInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("99999");
        assertNull(testCustomer, "Non-existent card number should give null Customer");
    }

    @Test
    public void garbageInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("243jklj42l4jk3jkl43hm");
        assertNull(testCustomer, "Garbage card number should give null Customer");
    }

    @Test
    public void tooShortInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("1234");
        assertNull(testCustomer, "Too short card number should give null Customer");
    }

    @Test
    public void tooLongInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("123456");
        assertNull(testCustomer, "Too long card number should give null Customer");
    }

    @Test
    public void emptyInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("");
        assertNull(testCustomer, "Empty card number should give null Customer");
    }

    @Test
    public void lateStartInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("12348");
        assertNull(testCustomer, "Card number with start date after current date should give null Customer");
    }

    @Test
    public void earlyExpiryInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("12347");
        assertNull(testCustomer, "Expired card should give null Customer");
    }

    @Test
    public void lostInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("12346");
        assertNull(testCustomer, "Lost card should give null Customer");
    }

    @Test
    public void stolenInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("12350");
        assertNull(testCustomer, "Stolen card should give null Customer");
    }

    @Test
    public void standardInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("12345");
        assertNotNull(testCustomer, "Standard user should return a non-null Customer object");
    }

    @Test
    public void adminInitCust() {
        ATM testATM = new ATM("0");
        Customer testCustomer = testATM.initCustomer("12321");
        assertNotNull(testCustomer, "Admin should return a non-null Customer object");
    }

    /** 
     * userCardNum() test - doesn't alter database
     *   Normal test
     *   Number starting with zeros
    */
    @Test
    public void userCardNumTest() {
        ATM testATM = new ATM("1");
        String input = "12345";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String cardNum = testATM.userCardNum();
        assertEquals("\n========= CARD NUMBER =========\n>> ENTER 5 DIGIT CARD NUMBER\n", outContent.toString());
        assertEquals("12345", cardNum);
    }

    @Test
    public void userCardNumZeroStart() {
        ATM testATM = new ATM("1");
        String input = "00023";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String cardNum = testATM.userCardNum();
        assertEquals("\n========= CARD NUMBER =========\n>> ENTER 5 DIGIT CARD NUMBER\n", outContent.toString());
        assertEquals("00023", cardNum);
    }

    /** 
     * displayOptions() test
    */
    @Test
    public void displayOptionsTest() {
        ATM testATM = new ATM("1");
        testATM.displayOptions();
        assertEquals("\nPlease select one of the options below:\n>> WITHDRAW from your account      type 1\n>> DEPOSIT into your account       type 2\n>> CHECK BALANCE of your account   type 3\n>> CANCEL and eject your card      type 0\n", outContent.toString());
    }

    /** 
     * validatePIN() test - doesn't alter database
     *   Correct PIN first try
     *   Incorrect PIN all 3 attempts - card is blocked message
     *   Correct PIN second try
    */
    @Test
    public void correctPIN() {
        ATM testATM = new ATM("1");
        Customer testCustomer = new Customer("09876");
        String input = "4867";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean correctPIN = testATM.validatePIN(testCustomer);
        assertTrue(correctPIN);
    }

    @Test
    public void correctSecondAttemptPIN() {
        ATM testATM = new ATM("1");
        Customer testCustomer = new Customer("09876");
        String input = "4866\n4867";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean correctPIN = testATM.validatePIN(testCustomer);
        assertTrue(correctPIN);
    }

    @Test
    public void incorrectPIN() {
        ATM testATM = new ATM("1");
        Customer testCustomer = new Customer("09876");
        String input = "4866\n4865\n4567";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean correctPIN = testATM.validatePIN(testCustomer);
        assertFalse(correctPIN);
    }

    /** 
     * ejectCard() test
     *   Admin
     *   Withdraw and deposit
     *   Standard user cancel
     *   Generic - none of the above
    */
    @Test
    public void adminEjectCard() {
        ATM testATM = new ATM("1");
        testATM.ejectCard("AC");
        assertEquals("Cancelling routine maintenance...\nYour card has been ejected. Have a nice day.\n============ X Y Z ============\n", outContent.toString());
    }

    @Test
    public void wdEjectCard() {
        ATM testATM = new ATM("1");
        testATM.ejectCard("WD");
        assertEquals("Transaction completed. Thank you for choosing XYZ bank.\nYour card has been ejected. Have a nice day.\n============ X Y Z ============\n", outContent.toString());
    }

    @Test
    public void userEjectCard() {
        ATM testATM = new ATM("1");
        testATM.ejectCard("CL");
        assertEquals("Cancelling...\nYour card has been ejected. Have a nice day.\n============ X Y Z ============\n", outContent.toString());
    }

    @Test
    public void otherEjectCard() {
        ATM testATM = new ATM("1");
        testATM.ejectCard("XX");
        assertEquals("Your card has been ejected. Have a nice day.\n============ X Y Z ============\n", outContent.toString());
    }

    /** 
     * runAdminSystem() test
     *   Cancel
     *   Non cash amount
     *   Non cash twice then correct cash amount
     *   Cash amount
    */
    @Test
    public void cancelAdmin() {
        ATM testATM = new ATM("5");
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean cashAdded = testATM.runAdminSystem();
        assertFalse(cashAdded);
    }

    @Test
    public void nonCashCancelAdmin() {
        ATM testATM = new ATM("5");
        String input = "54.43\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean cashAdded = testATM.runAdminSystem();
        assertFalse(cashAdded);
    }

    @Test
    public void nonCashTwiceCorrectOnceAdmin() {
        ATM testATM = new ATM("5");
        String input = "54.43\n5489.808\n23";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean cashAdded = testATM.runAdminSystem();
        assertTrue(cashAdded);
    }

    @Test
    public void cashAdmin() {
        ATM testATM = new ATM("5");
        String input = "54.45";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean cashAdded = testATM.runAdminSystem();
        assertTrue(cashAdded);
    }

    /** 
     * runStandardSystem(Customer) test
     *   Cancel
     *   Check balance then cancel
     *   Invalid entry then cancel
    */
    @Test
    public void cancelStdSystem() {
        ATM testATM = new ATM("5");
        Customer testCustomer = new Customer("00123");
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean withdrawDeposit = testATM.runStandardSystem(testCustomer);
        assertFalse(withdrawDeposit);
    }

    @Test
    public void checkBalCancelStdSystem() {
        ATM testATM = new ATM("5");
        Customer testCustomer = new Customer("00123");
        String input = "3\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean withdrawDeposit = testATM.runStandardSystem(testCustomer);
        assertFalse(withdrawDeposit);
    }

    @Test
    public void invalidCancelStdSystem() {
        ATM testATM = new ATM("5");
        Customer testCustomer = new Customer("00123");
        String input = "5\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean withdrawDeposit = testATM.runStandardSystem(testCustomer);
        assertFalse(withdrawDeposit);
    }
}
