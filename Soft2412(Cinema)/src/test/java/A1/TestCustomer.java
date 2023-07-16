package A1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestCustomer {
    private Customer customer;
    @Test
    public void testConstructor(){
        customer = new Customer("12321");
        assertEquals("12321", customer.getCardNum());
        assertEquals(CardStatus.ACTIVE, customer.getCardStatus());
        assertTrue(customer.isAdmin());
        assertEquals("0120", customer.getStartDate());
        assertEquals("0124", customer.getExpDate());
        assertEquals("1234", customer.getPIN());

        customer = new Customer("12346");
        assertEquals("12346", customer.getCardNum());
        assertEquals(CardStatus.LOST, customer.getCardStatus());
        assertFalse(customer.isAdmin());
        assertEquals("1220", customer.getStartDate());
        assertEquals("1121", customer.getExpDate());
        assertEquals("1001", customer.getPIN());

        customer = new Customer("09090");
        assertEquals("09090", customer.getCardNum());
        assertEquals(CardStatus.STOLEN, customer.getCardStatus());
        assertFalse(customer.isAdmin());
        assertEquals("0720", customer.getStartDate());
        assertEquals("0722", customer.getExpDate());
        assertEquals("0000", customer.getPIN());


    }


    @Test
    public void testCheckPin(){
        customer = new Customer("12347");
        assertTrue(customer.checkPIN("0804"));

        customer = new Customer("12348");
        assertTrue(customer.checkPIN("1903"));
    }

    @Test
    public void testBlockCard(){
        customer = new Customer("10101");
        customer.blockCard();
        assertEquals(CardStatus.BLOCKED, customer.getCardStatus());
        assertEquals(CardStatus.BLOCKED, Crud.getCardStatus("10101"));
    }

    @Test
    public void testUpdateBalance(){
        customer = new Customer("12345");
        float expectedBalance = (float) (Crud.availableFunds("12345") - 10.0);
        customer.updateBalance(Crud.availableFunds("12345")-10, TransactionType.WITHDRAW);
        assertEquals(expectedBalance, customer.getCardBalance());
    }


}
