package A1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestWithdraw {


    @Test
    public void testuserinput_valid(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Withdraw withdraw = new Withdraw(atm.getNextTransactionNum(),cus,atm);

        String input = "10";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals( 10 , withdraw.inputUserAmount(cus));
    }

    @Test
    public void testprintreciept(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Withdraw withdraw = new Withdraw(atm.getNextTransactionNum(),cus,atm);

        String input = "10";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals( -1 , withdraw.printReceipt());
    }

    @Test
    public void testdiplaycashdispense(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Withdraw withdraw = new Withdraw(atm.getNextTransactionNum(),cus,atm);

        String input = "10";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals( -1 , withdraw.displayDispenseCash());
    }

    @Test
    public void testdiplaydeny(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Withdraw withdraw = new Withdraw(atm.getNextTransactionNum(),cus,atm);

        String input = "10000000000000000000";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        withdraw.doTransaction();

        assertTrue(withdraw.displayWithdrawDenied());
    }

    @Test
    public void testdiplaydeny_atm(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("1");

        Withdraw withdraw = new Withdraw(atm.getNextTransactionNum(),cus,atm);

        String input = "10";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        withdraw.doTransaction();

        assertTrue(withdraw.displayWithdrawDenied());
    }

    @Test
    public void testdotransaction(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Withdraw withdraw = new Withdraw(atm.getNextTransactionNum(),cus,atm);

        String input = "10";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertFalse(withdraw.displayWithdrawDenied());

        withdraw.doTransaction();


    }


}
