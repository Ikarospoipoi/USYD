package A1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCheckBalance {
    private CheckBalance CB;
    private Customer customer = new Customer("12345");
    private ATM atm = new ATM("3");

    @Test
    public void testConstruct(){

        CB = new CheckBalance(Crud.lastTransactionNumber("3") + 1, customer, atm);
        assertNotNull(CB);
    }

    @Test
    public void testDoTransaction(){
        PrintStream stdout = System.out;
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outPutStream));

        CB = new CheckBalance(Crud.lastTransactionNumber("12345") + 1, customer, atm);

        CB.doTransaction();

        assertEquals(String.format("Your account balance is: $%.2f", Crud.availableFunds("12345")), outPutStream.toString().trim());

        System.setOut(stdout);


    }

}
