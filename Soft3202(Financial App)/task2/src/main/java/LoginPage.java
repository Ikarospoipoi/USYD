import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class LoginPage extends Application {
    GetFromWeb getFromWeb = new GetFromWeb();
    private static String argument;

    public static void main(String[] args) {
        argument = args[0];
        launch(args);
    }


    public void start(Stage primaryStage) {
        // Create a label
        primaryStage.setTitle("Welcome to spacetrader");

        Scene scene = loginScence(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene loginScence(Stage primaryStage) {

        Scene scene;

        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        Text scenetitle = new Text("Welcome to Spacetrader");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setPromptText("Enter your username");
        grid.add(userTextField, 1, 1);

        Label token = new Label("Token");
        grid.add(token, 0, 2);

        TextField tokenTextField = new TextField();
        tokenTextField.setPromptText("Enter your token");
        grid.add(tokenTextField, 1, 2);

        Button button1 = new Button("Register with username");
        Button button2 = new Button("Log in with token");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);
        grid.add(button1, 2, 1);
        grid.add(button2, 2, 2);

        Label label = new Label("");
        grid.add(label, 0, 3,5,10);

        button1.setOnAction((event) -> {
            String text = getFromWeb.Register(userTextField.getText(),argument);
            label.setText(text);
        });


        button2.setOnAction((event) -> {
            String token_num = tokenTextField.getText();
            String text = getFromWeb.LoginWithToken(token_num, argument);
            if (text.equals("message: Token was invalid or missing from the request. Did you confirm sending the token as a query parameter or authorization header?")) {
                label.setText(text);
            } else {
                MainMenu mainMenu = new MainMenu(text, token_num, argument);
                mainMenu.start(primaryStage);
            }
        });

        scene = new Scene(grid, 800, 800);
        return scene;
    }
}

