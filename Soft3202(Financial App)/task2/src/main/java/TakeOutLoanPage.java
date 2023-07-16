import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TakeOutLoanPage extends Application {
    GetFromWeb getFromWeb = new GetFromWeb();
    private String text;
    private String token;
    private String argument;
    public TakeOutLoanPage(String token,String text, String argument) {
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

        Text scenetitle = new Text("Here is your credits");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label lblNewLabel = new Label(getFromWeb.TakeoutLoan(token,argument));
        grid.add(lblNewLabel, 0, 1,8,8);

        Button btnNewButton = new Button("Back");
        btnNewButton.setOnAction(e -> {
            MainMenu mainMenuPage = new MainMenu(text,token, argument);
            mainMenuPage.start(primaryStage);
        });
        grid.add(btnNewButton, 0, 9);

//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);




        Scene scene = new Scene(grid, 800, 800);
        return scene;
    }
}
