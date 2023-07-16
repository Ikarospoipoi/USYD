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

public class PurchaseFuelPage extends Application {
    GetFromWeb getFromWeb = new GetFromWeb();
    private String token;
    private String ShipID;
    private String quantity;
    private String MainMenuText;
    private String argument;
    public PurchaseFuelPage(String token,String mainMenuText, String argument) {
        this.token = token;
        this.MainMenuText = mainMenuText;
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

        Text scenetitle = new Text("Purchase");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);
//        String text1 = getFromWeb.PurchaseShip(token, type, location,0, argument);
//        Label label = new Label(text1);
//        grid.add(label, 0, 3, 5, 10);
//
        TextField shipID = new TextField();
        shipID.setPromptText("Enter the Ship ID you would like to purchase good for");
        grid.add(shipID, 0, 3, 3, 1);

        TextField quantity = new TextField();
        quantity.setPromptText("Enter your the quantity of goods you would like to purchase");
        grid.add(quantity, 0, 5, 3, 1);

        TextField good = new TextField();
        good.setPromptText("Enter your the good you would like to purchase");
        grid.add(good, 0, 4, 3, 1);
        Button button1 = new Button("Purchase");
        grid.add(button1, 0, 6);
        Label label = new Label();
        grid.add(label, 0, 7, 20, 20);
        button1.setOnAction(e -> {
            String text1 = getFromWeb.PurchaseShipFuel(token, shipID.getText(), quantity.getText(), good.getText(), argument);
            label.setText(text1);
        });




        Button button2 = new Button("Back to Main Menu");
        grid.add(button2, 5, 5);
        button2.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(MainMenuText, token, argument);
            mainMenu.start(primaryStage);
        });




        Scene scene = new Scene(grid, 800, 800);
        return scene;
    }

}

