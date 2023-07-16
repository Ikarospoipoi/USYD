package milestone2.view;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CompanyResult {
    private String argument;
    private String result;
    private String argumrnt_pastebin;
    private GridPane grid;
    private CompanyPojo SavedCompanyPojo;
    public String currentCompany;
    private String flag;
    private String type;
    public CompanyResult(String argument, String argumrnt_pastebin, String result, GridPane grid, HBox hbox, CompanyPojo SavedCompanyPojo, String current_company, String flag, String type) {
        this.argument = argument;
        this.result = result;
        this.argumrnt_pastebin = argumrnt_pastebin;
        this.grid = grid;
        this.SavedCompanyPojo = SavedCompanyPojo;
        this.currentCompany = current_company;
        this.flag = flag;
        this.type = type;
    }

    /**
     * This method is used to create the main scene of CompanyResult
     * @param primaryStage
     * @return
     */
    public GridPane MianScence(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        try{
            String[] result_output = result.split("------------------------------\n");
            int rowindex = 0;
            int columnindex = 0;

            for (int i = 0; i < result_output.length; i++) {
                Label label = new Label("Symbol: " + result_output[i]);
                String CompanyName = result_output[i];
                grid.add(label, columnindex, rowindex, 5, 2);
                Button button = new Button("Go to Company");
                button.setOnAction(e -> {
                    ProgressIndicator progressIndicator = new ProgressIndicator();
                    progressIndicator.setMaxSize(100, 100);
                    progressIndicator.setMinSize(100, 100);
                    progressIndicator.setPrefSize(100, 100);
                    progressIndicator.setVisible(true);
                    progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                    grid.add(progressIndicator, 0, 0, 5, 2);
                    CompanyDetail companyDetail = new CompanyDetail(argument, argumrnt_pastebin, CompanyName,SavedCompanyPojo, currentCompany, flag, type);

                    companyDetail.Start(primaryStage);
                });
                grid.add(button, columnindex, rowindex + 2);
                rowindex += 5;
                if (rowindex >= 17) {
                    rowindex = 0;
                    columnindex += 1;
                }
            }
        } catch (Exception e) {
            Label label = new Label("Error:\n You have searched an invalid Company\n Or you have hit the 5 times API Call limit.\n");

            grid.add(label, 0, 0, 5, 2);
        }
        return grid;
    }

    public void addtoMain(Stage primaryStage) {
        grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
        grid.add(MianScence(primaryStage), 0, 1);

    }
}
