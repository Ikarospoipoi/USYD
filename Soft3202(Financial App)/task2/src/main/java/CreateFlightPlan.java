import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateFlightPlan extends Application {
    GetFromWeb getFromWeb = new GetFromWeb();
    private String token;
    private String MianMenuText;;
    private String argument;
    public CreateFlightPlan(String token, String MainMenuText, String argument) {
        this.token = token;
        this.MianMenuText = MainMenuText;
        this.argument = argument;
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        // Create a label
        primaryStage.setTitle("Welcome to spacetrader");

        Scene scene = ShowShips(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene ShowShips(Stage primaryStage) {
        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        Text scenetitle = new Text("You can Create Flight Plan");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
        grid.add(scenetitle, 0, 0, 2, 1);

        TextField shipID = new TextField();
        shipID.setPromptText("Enter Ship ID");
        grid.add(shipID, 0, 1);

        TextField detination = new TextField();
        detination.setPromptText("Enter Destination");
        grid.add(detination, 0, 2);

        Label result = new Label();
        grid.add(result, 0, 4);


        Button btn = new Button("Create");
        grid.add(btn, 0, 3);
        btn.setOnAction(e -> {
            String shipIDText = shipID.getText();
            String detinationText = detination.getText();
            String resultText = getFromWeb.CreateFlightPlan(token, shipIDText, detinationText,argument);
            result.setText(resultText);
        });

//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);


        Button button1 = new Button("Back to Main Menu");
        grid.add(button1, 0, 30, 2, 1);
        button1.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(MianMenuText, token, argument);
            mainMenu.start(primaryStage);
        });




        Scene scene = new Scene(grid, 800, 800);
        return scene;
    }

}

