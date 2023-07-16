package A1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class Testdeposit {


    @Test
    public void testuserinput_valid(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Deposit dep = new Deposit(atm.getNextTransactionNum(),cus,atm);

        String input = "500";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals( 500 , dep.inputUserAmount());

    }


    @Test
    public void update(){
        float a =  Crud.availableFunds("12345");
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Deposit dep = new Deposit(atm.getNextTransactionNum(),cus,atm);

        String input = "500";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        dep.doTransaction();
        assertEquals( a + 500, Crud.availableFunds("12345"));

    }

    @Test
    public void testuserinput_amount(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Deposit dep = new Deposit(atm.getNextTransactionNum(),cus,atm);

        String input = "500";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals( 1 , dep.displayDepositCash());

    }




    @Test
    public void testprint_receipt(){
        Customer cus = new Customer("12345");
        ATM atm = new ATM("0");

        Deposit dep = new Deposit(atm.getNextTransactionNum(),cus,atm);

        String input = "500";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals( -1 , dep.printReceipt());

    }



}