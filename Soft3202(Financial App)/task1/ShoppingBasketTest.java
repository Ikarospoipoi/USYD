package au.edu.sydney.soft3202.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingBasketTest {
    @Test
    public void testAddItem() {

        ShoppingBasket basket = new ShoppingBasket();

        basket.addItem("apple", 1);
        assertEquals("apple", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());
        basket.addItem("ApPle", 1);
        assertEquals("apple", basket.getItems().get(0).getKey());
        assertEquals(2, basket.getItems().get(0).getValue());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.addItem("", 1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.addItem("", -1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.addItem(null, 1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.addItem("uyetfryugdsf", 1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.addItem("apple", 0);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.addItem("apple", -1);
        });
    }

    @Test
    public void testRemoveItems() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Apple", 2);
        basket.addItem("Orange", 2);
        basket.addItem("Banana", 2);
        assertEquals(3, basket.getItems().size());

        assertTrue(basket.removeItem("Apple", 1));
        assertFalse(basket.removeItem("Apple", 2));
        assertFalse(basket.removeItem("Pear", 1));
        assertFalse(basket.removeItem("", 1)); //This is not sure
        //assertFalse(basket.removeItem(null, 1));


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.removeItem("Apple", -1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basket.removeItem(null, 0);
        });

    }

    @Test
    public void testGetItems() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.clear();
        basket.addItem("apple", 1);
        basket.addItem("orange", 1);
        assertEquals(2, basket.getItems().size());
        assertEquals("apple", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());
        assertEquals("orange", basket.getItems().get(1).getKey());
        assertEquals(1, basket.getItems().get(1).getValue());

        basket.addItem("apple", 1);
        boolean flag = false;
        for (Pair<String, Integer> item : basket.getItems()) {
            if (item.getKey().equals("apple")) {
                int a = item.getValue();
                if (a == 2) {
                    flag = true;
                }
            }
        }
        assertTrue(flag);
    }

    @Test
    public void testClear() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Apple", 1);
        basket.addItem("Orange", 1);
        assertEquals(2, basket.getItems().size());
        basket.clear();
        assertEquals(0, basket.getItems().size());
    }

    @Test
    public void testGetValue() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Apple", 1);
        basket.addItem("Orange", 1);
        basket.addItem("Banana", 1);
        basket.addItem("Pear", 1);
//        assertEquals(2, basket.getItems().size());
//        List<Pair<String,Integer>> items = basket.getItems();
//        double total = 0;
//        for (Pair<String,Integer> item : items) {
//            if (item.getKey().equals("Apple")) {
//                total += 2.50 * item.getValue();;
//            }
//            else if (item.getKey().equals("Orange")) {
//                total += 1.25 * item.getValue();
//            }
//            else if (item.getKey().equals("Banana")) {
//                total += 4.95 * item.getValue();
//            }
//            else if (item.getKey().equals("Pear")) {
//                total += 3.00 * item.getValue();
//            }
//        }
        assertTrue(basket.getValue() == 11.7);
        basket.clear();
        assertEquals(0, basket.getItems().size());
        assertNull(basket.getValue());
    }
}
