package milestone2;

import milestone2.model.HandleInput;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static org.testng.AssertJUnit.assertEquals;

public class TestHandleInput {

    @org.junit.jupiter.api.Test
    public void testOperatingCashFlow() {
        HandleInput handleInput = new HandleInput();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operatingCashflow", "100");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        ArrayList<Long> arrayList =  handleInput.OperatingCashFlow(jsonArray,"online");
        assertEquals(arrayList.get(0).longValue(), 100L);

        ArrayList<Long> arrayList1 =  handleInput.OperatingCashFlow(jsonArray,"offline");
        assertEquals(arrayList1.get(0).longValue(), 0L);
    }

    @org.junit.jupiter.api.Test
    public void testGetYear() {
        HandleInput handleInput = new HandleInput();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fiscalDateEnding", "2018-01-01");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        ArrayList<String> arrayList =  handleInput.GetYear(jsonArray,"online");
        assertEquals(arrayList.get(0), "2018-01-01");

        ArrayList<String> arrayList1 =  handleInput.GetYear(jsonArray,"offline");
        assertEquals(arrayList1.get(0).toString(), "2017");
    }

    @org.junit.jupiter.api.Test
    public void testGetcapitalExpenditures() {
        HandleInput handleInput = new HandleInput();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("capitalExpenditures", "100");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        ArrayList<Long> arrayList =  handleInput.GetcapitalExpenditures(jsonArray,"online");
        assertEquals(arrayList.get(0).longValue(), 100);

        ArrayList<Long> arrayList1 =  handleInput.GetcapitalExpenditures(jsonArray,"offline");
        assertEquals(arrayList1.get(0).longValue(), 0L);
    }

    @org.junit.jupiter.api.Test
    public void testGetprofitLoss() {
        HandleInput handleInput = new HandleInput();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("profitLoss", "100");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        ArrayList<Long> arrayList =  handleInput.GetprofitLoss(jsonArray,"online");
        assertEquals(arrayList.get(0).longValue(), 100);

        ArrayList<Long> arrayList1 =  handleInput.GetprofitLoss(jsonArray,"offline");
        assertEquals(arrayList1.get(0).longValue(), 0L);
    }

    @org.junit.jupiter.api.Test
    public void testGetdividendPayout(){
        HandleInput handleInput = new HandleInput();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dividendPayout", "100");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        ArrayList<Long> arrayList =  handleInput.GetDividendPayout(jsonArray,"online");
        assertEquals(arrayList.get(0).longValue(), 100);

        ArrayList<Long> arrayList1 =  handleInput.GetDividendPayout(jsonArray,"offline");
        assertEquals(arrayList1.get(0).longValue(), 0L);
    }

    @org.junit.jupiter.api.Test
    public void testGetnetIncome(){
        HandleInput handleInput = new HandleInput();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("netIncome", "100");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        ArrayList<Long> arrayList =  handleInput.GetnetIncome(jsonArray,"online");
        assertEquals(arrayList.get(0).longValue(), 100);

        ArrayList<Long> arrayList1 =  handleInput.GetnetIncome(jsonArray,"offline");
        assertEquals(arrayList1.get(0).longValue(), 0L);
    }
}

