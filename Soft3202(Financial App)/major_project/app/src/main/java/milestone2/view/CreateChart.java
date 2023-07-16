package milestone2.view;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CreateChart {
    /**
     * Creates a line chart with the given data
     * @param profitloss
     * @param year
     * @param title
     * @return
     */
    public LineChart<String, Number> getStringNumberLineChart(ArrayList<Long> profitloss, ArrayList<String> year, String title) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        final LineChart<String, Number> lineChart = new LineChart<String, Number>(
                xAxis, yAxis);

        lineChart.setTitle("Line Chart");
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName(title);
        try {
            if (profitloss.get(0) == -1l) {
                Text text = new Text("Error: This is a free version of AlphaVantage. You can call it 5 times per minute. If you want to use it more than 5 times per minute, you need to upgrade to AlphaVantage Premium.");
                text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            } else {
                // populating the series with data
                if (profitloss.size() > 0) {
                    for (int i = 0; i < profitloss.size(); i++) {
                        if (profitloss.get(i) != 0) {
                            series.getData().add(new XYChart.Data<String, Number>(year.get(i), profitloss.get(i)));
                        }
                    }
                    lineChart.getData().add(series);
                } else {
                    Text text = new Text("No Data");
                    text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                }
            }
        } catch (Exception e) {
            Text text = new Text("Data Error");
            text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        }
        return lineChart;
    }
}
