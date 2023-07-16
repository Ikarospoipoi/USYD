package A1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    public void testisCashDivisable() {
        assertTrue(Transaction.isCashDivisable(5, true));
        assertFalse(Transaction.isCashDivisable(-10, true));
        assertFalse(Transaction.isCashDivisable(-10, false));
        assertFalse(Transaction.isCashDivisable(-30, false));
        assertFalse(Transaction.isCashDivisable(4.91, false));
        assertFalse(Transaction.isCashDivisable(4.91, true));
        assertFalse(Transaction.isCashDivisable(2.5, true));
        //  assertFalse(Transaction.isCashDivisable(4, true));

    }

}
