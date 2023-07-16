package milestone2;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import milestone2.model.GetFromWeb;
import milestone2.model.HandleInput;
import milestone2.model.PasatebinString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestPastebintoString {
    private HandleInput handleInput;
    private JsonArray jsonArray;
    private ArrayList<Long> arrayList;
    private ArrayList<String> arrayList1;

    @BeforeEach
    public void setUp() {
        jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("date", "2019-01-01");
        jsonObject.addProperty("dividendPayout", 1);
        jsonObject.addProperty("profitLossString", 1);
        jsonObject.addProperty("capitalExpenditures", 1);
        jsonObject.addProperty("netIncome", 1);
        jsonObject.addProperty("operatingCashflow", 1);
        jsonArray.add(jsonObject);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
    }
    @Test
    public void testPastebintoString() {
        PasatebinString pasatebinString = new PasatebinString("bank", "online","online", jsonArray, arrayList,arrayList1,arrayList,arrayList,arrayList,arrayList);
        String result = "";
        assertEquals(result, pasatebinString.SetString());

        PasatebinString pasatebinString1 = new PasatebinString("bank", "offline","online", jsonArray, arrayList,arrayList1,arrayList,arrayList,arrayList,arrayList);
        assertEquals("Company Name: BA", pasatebinString1.SetString());
    }

    @Test
    public void testCompanyDetail(){
        GetFromWeb getFromWeb = mock(GetFromWeb.class);
        when(getFromWeb.CompanyDetail("bank","online")).thenReturn("Detail");
        PasatebinString pastebin = new PasatebinString("bank", "online","online", jsonArray, arrayList,arrayList1,arrayList,arrayList,arrayList,arrayList);
        pastebin.setGetFromWeb(getFromWeb);
        PasatebinString pastebin1 = new PasatebinString("bank", "online","online", jsonArray, arrayList,arrayList1,arrayList,arrayList,arrayList,arrayList);
        pastebin1.setGetFromWeb(getFromWeb);

        assertEquals("Detail", pastebin.GetCompanyDetail());
        assertEquals("Detail", pastebin1.GetCompanyDetail());
    }

    @Test
    public void getFinalresult(){
        PasatebinString pastebin1 = new PasatebinString("bank", "online","online",  jsonArray, arrayList,arrayList1,arrayList,arrayList,arrayList,arrayList);

        assertEquals("\n"+"\n", pastebin1.FinalOutput());
    }
}
