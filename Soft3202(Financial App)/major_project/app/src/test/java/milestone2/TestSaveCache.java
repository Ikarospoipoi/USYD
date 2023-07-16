package milestone2;

import com.google.gson.JsonArray;
import milestone2.model.Database;
import milestone2.model.HandleInput;
import milestone2.model.SaveCache;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestSaveCache {
    @Test
    public void TestChangeCache(){
        HandleInput handleInput = mock(HandleInput.class);
        Database dataBase = mock(Database.class);
        ArrayList<String> list = new ArrayList<>();
        list.add("date");
        ArrayList<Long> list2 = new ArrayList<>();
        list2.add(10L);
        JsonArray jsonArray = new JsonArray();
        when(handleInput.GetcapitalExpenditures(eq(jsonArray),anyString())).thenReturn(list2);
        when(handleInput.GetnetIncome(eq(jsonArray),anyString())).thenReturn(list2);
        when(handleInput.GetDividendPayout(eq(jsonArray),anyString())).thenReturn(list2);
        when(handleInput.GetprofitLoss(eq(jsonArray),anyString())).thenReturn(list2);
        when(handleInput.GetYear(eq(jsonArray),anyString())).thenReturn(list);
        when(handleInput.OperatingCashFlow(eq(jsonArray),anyString())).thenReturn(list2);


        SaveCache cache = new SaveCache("online","test",jsonArray);
        cache.setDatabase(dataBase);
        cache.setHandleInput(handleInput);
        assertTrue(cache.Change_Cache("add"));
        verify(dataBase,times(1)).add_cache(any(),anyLong(),anyLong(),anyLong(),anyLong(),anyLong(),any());
        assertTrue(cache.Change_Cache("update"));
        verify(dataBase,times(1)).DeleteSpecificCompany(any());
    }
}
