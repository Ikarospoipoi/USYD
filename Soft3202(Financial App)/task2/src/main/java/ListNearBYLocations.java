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

public class ListNearBYLocations extends Application {
    GetFromWeb getFromWeb = new GetFromWeb();
    private String text;
    private String token;
    private String argument;
    public ListNearBYLocations(String token,String text, String argument) {
        this.text = text;
        this.token = token;
        this.argument = argument;
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        // Create a label
        primaryStage.setTitle("Welcome to spacetrader");

        Scene scene = MianScence(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public Scene MianScence(Stage primaryStage) {
        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        Text scenetitle = new Text("Here is the list of locations near by");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        grid.add(scenetitle, 0, 0, 2, 1);

        TextField textField = new TextField();
        textField.setPromptText("Enter the location type you would like to search");
        grid.add(textField, 0, 1);

        Label label = new Label("");
        grid.add(label, 0,5,20,20 );

        Button btn = new Button("Search");
        btn.setOnAction(e -> {
            String text1 = textField.getText();
            label.setText(getFromWeb.FindNearbylocation(token, text1, argument));
        });
        grid.add(btn, 1, 1);




        Button btnNewButton = new Button("Back to Main Menu");
        btnNewButton.setOnAction(e -> {
            MainMenu mainMenuPage = new MainMenu(text,token, argument);
            mainMenuPage.start(primaryStage);
        });
        grid.add(btnNewButton, 5, 1);

//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);




        Scene scene = new Scene(grid, 800, 800);
        return scene;
    }
}

