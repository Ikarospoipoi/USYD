package milestone2;

import com.google.gson.JsonArray;
import milestone2.model.CompanyDetailHandler;
import milestone2.model.PasteHandler;
import milestone2.model.GetFromWeb;
import milestone2.model.Pastebin;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestHandler {
    @Test
    public void testCompanyDetail(){
        GetFromWeb getFromWeb = mock(GetFromWeb.class);
        when(getFromWeb.CompanyDetail(anyString(),anyString())).thenReturn("Detail");
        CompanyDetailHandler cdc = new CompanyDetailHandler("ba","online");
        cdc.setGetFromWeb(getFromWeb);
        CompanyDetailHandler cdc1 = new CompanyDetailHandler("ba","offline");
        cdc1.setGetFromWeb(getFromWeb);
        assertEquals("Detail", cdc.CompanyDetail());
        assertEquals("Detail", cdc1.CompanyDetail());
        verify(getFromWeb,times(2)).CompanyDetail(anyString(),anyString());
    }

    @Test
    public void testCompanyfinancialDetail(){
        JsonArray jsonArray = new JsonArray();
        GetFromWeb getFromWeb = mock(GetFromWeb.class);
        when(getFromWeb.CompanyFinancialDetail(anyString(),anyString())).thenReturn(jsonArray);
        CompanyDetailHandler cdc = new CompanyDetailHandler("ba","online");
        cdc.setGetFromWeb(getFromWeb);
        CompanyDetailHandler cdc1 = new CompanyDetailHandler("ba","offline");
        cdc1.setGetFromWeb(getFromWeb);
        assertEquals(jsonArray, cdc.CompanyFinacial());
        assertEquals(jsonArray, cdc1.CompanyFinacial());
        verify(getFromWeb,times(2)).CompanyFinancialDetail(anyString(),anyString());
    }

    @Test
    public void testPaste() throws IOException, InterruptedException {
        Pastebin pastebin = mock(Pastebin.class);
        when(pastebin.post(anyString(),anyString())).thenReturn("Paste");
        PasteHandler pc = new PasteHandler("ba","online",pastebin);
        pc.setPastebin(pastebin);
        PasteHandler pc1 = new PasteHandler("ba","offline",pastebin);
        pc1.setPastebin(pastebin);
        assertEquals("Paste", pc.postResult());
        assertEquals("Paste", pc1.postResult());
        verify(pastebin,times(2)).post(anyString(),anyString());
    }
}
