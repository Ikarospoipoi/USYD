package milestone2.view;

import javafx.scene.chart.Chart;

import java.util.ArrayList;

public class CompanyPojo {
    public String companyName;
    public String type;
    private ArrayList<Long> Saved_Data;
    public Chart chart;

    public CompanyPojo(String companyName, String type, ArrayList<Long> Saved_Data, Chart chart) {
        this.companyName = companyName;
        this.type = type;
        this.Saved_Data = Saved_Data;
        this.chart = chart;
    }
    /**
     * This method is used to get the company name
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * This method is used to get the company type
     */
    public String getType() {
        return type;
    }
    /**
     * This method is used to get the company data
     */
    public ArrayList<Long> getSaved_Data() {
        return Saved_Data;
    }
    /**
     * This method is used to get the chart
     */
    public Chart getChart() {
        return chart;
    }
}
