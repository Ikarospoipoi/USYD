package milestone2;

import milestone2.model.Database;
import milestone2.model.DatabaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

public class TestDatabaseHandler {
    private DatabaseHandler dbHandler;
    private ArrayList<String> Date;
    private ArrayList<Long> SimulatedData;
    private Database database;
    @BeforeEach
    public void setUp() {
        Date = new ArrayList<>();
        Date.add("test");
        SimulatedData = new ArrayList<>();
        SimulatedData.add(10L);
        database = mock(Database.class);
        when(database.SelectDate(anyString())).thenReturn(Date);
        when(database.SelectCapitalExpenditure(anyString())).thenReturn(SimulatedData);
        when(database.SelectDividendPayout(anyString())).thenReturn(SimulatedData);
        when(database.SelectNetIncome(anyString())).thenReturn(SimulatedData);
        when(database.SelectOperatingCashflow(anyString())).thenReturn(SimulatedData);
        when(database.SelectProfitLoss(anyString())).thenReturn(SimulatedData);
        dbHandler = new DatabaseHandler("test");
        dbHandler.setDatabase(database);

    }
    @Test
    public void TestGetDate() {

        assertEquals(dbHandler.GetDate(),Date);
        verify(database, times(1)).SelectDate("test");
    }

    @Test
    public void TestGetCapitalExpenditure() {
        assertEquals(dbHandler.GetCapitalExpenditure(),SimulatedData);
        verify(database, times(1)).SelectCapitalExpenditure("test");
    }

    @Test
    public void TestGetDividendPayout() {
        assertEquals(dbHandler.GetDividendPayout(),SimulatedData);
        verify(database, times(1)).SelectDividendPayout("test");
    }

    @Test
    public void TestGetNetIncome() {
        assertEquals(dbHandler.GetNetIncome(),SimulatedData);
        verify(database, times(1)).SelectNetIncome("test");
    }

    @Test
    public void TestGetOperatingCashflow() {
        assertEquals(dbHandler.GetOperatingCashflow(),SimulatedData);
        verify(database, times(1)).SelectOperatingCashflow("test");
    }

    @Test
    public void TestGetProfitLoss() {
        assertEquals(dbHandler.GetProfitLoss(),SimulatedData);
        verify(database, times(1)).SelectProfitLoss("test");
    }

    @Test
    public void TestAdd_Delete(){

    }
}
