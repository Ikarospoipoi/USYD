package milestone2;

import javafx.scene.chart.Chart;
import milestone2.model.CompareCompany;
import milestone2.view.CompanyPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCompareCompany {
    private String companyName;
    private String type;
    private ArrayList<Long> Compare_Data;
    private ArrayList<Long> Data;
    private Chart chart;
    private CompanyPojo companyPojo;
    private CompanyPojo CompareCompany;

    @Test
    public void testCompareCompany() {
        companyName = "Apple";
        type = "Net Income";
        Compare_Data = new ArrayList<>();
        Compare_Data.add(1L);
        companyPojo = mock(CompanyPojo.class);
        when(companyPojo.getCompanyName()).thenReturn(companyName);
        when(companyPojo.getType()).thenReturn(type);
        when(companyPojo.getSaved_Data()).thenReturn(Compare_Data);
        when(companyPojo.getChart()).thenReturn(chart);

        companyName = "Google";
        type = "Net Income";
        Compare_Data = new ArrayList<>();
        Compare_Data.add(2L);
        CompareCompany = mock(CompanyPojo.class);
        when(CompareCompany.getCompanyName()).thenReturn(companyName);
        when(CompareCompany.getType()).thenReturn(type);
        when(CompareCompany.getSaved_Data()).thenReturn(Compare_Data);
        when(CompareCompany.getChart()).thenReturn(chart);
        CompareCompany compareCompany = new CompareCompany(companyPojo, CompareCompany,"online");
        assertEquals("This company's Net Income chart value is bigger!", compareCompany.CompareData());
    }

    @Test
    public void testCompareCompany2() {
        companyName = "Apple";
        type = "Net Income";
        Compare_Data = new ArrayList<>();
        Compare_Data.add(-1L);
        companyPojo = mock(CompanyPojo.class);
        when(companyPojo.getCompanyName()).thenReturn(companyName);
        when(companyPojo.getType()).thenReturn(type);
        when(companyPojo.getSaved_Data()).thenReturn(Compare_Data);
        when(companyPojo.getChart()).thenReturn(chart);

        companyName = "Google";
        type = "Net Income";
        Compare_Data = new ArrayList<>();
        Compare_Data.add(2L);
        CompareCompany = mock(CompanyPojo.class);
        when(CompareCompany.getCompanyName()).thenReturn(companyName);
        when(CompareCompany.getType()).thenReturn(type);
        when(CompareCompany.getSaved_Data()).thenReturn(Compare_Data);
        when(CompareCompany.getChart()).thenReturn(chart);

        CompareCompany compareCompany = new CompareCompany(companyPojo, CompareCompany,"online");
        assertEquals("No Data", compareCompany.CompareData());
    }

    @Test
    public void testCompareCompany3() {
        companyName = "Apple";
        type = "Net Income";
        Compare_Data = new ArrayList<>();
        Compare_Data.add(2L);
        companyPojo = mock(CompanyPojo.class);
        when(companyPojo.getCompanyName()).thenReturn(companyName);
        when(companyPojo.getType()).thenReturn(type);
        when(companyPojo.getSaved_Data()).thenReturn(Compare_Data);
        when(companyPojo.getChart()).thenReturn(chart);

        companyName = "Google";
        type = "Net Income";
        Compare_Data = new ArrayList<>();
        Compare_Data.add(1L);
        CompareCompany = mock(CompanyPojo.class);
        when(CompareCompany.getCompanyName()).thenReturn(companyName);
        when(CompareCompany.getType()).thenReturn(type);
        when(CompareCompany.getSaved_Data()).thenReturn(Compare_Data);
        when(CompareCompany.getChart()).thenReturn(chart);

        CompareCompany compareCompany = new CompareCompany(companyPojo, CompareCompany,"online");
        assertEquals("False", compareCompany.CompareData());
    }


}
