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

public class ShowShips extends Application {
    GetFromWeb getFromWeb = new GetFromWeb();
    private String token;
    private String mainMenuText;
    private String argument;

    public ShowShips(String token, String mainMenuText, String argument) {
        this.mainMenuText = mainMenuText;
        this.token = token;
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
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        Text scenetitle = new Text("Ships Market");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);
        String text1 = getFromWeb.GetShips(token, argument);
        String[] text2 = text1.split("------------------------------\n");
        int colunm_index =0;
        int row_index = 2;
        for (int i = 0; i < text2.length; i++) {
            Label label = new Label(text2[i] +"------------------------------\n");
            grid.add(label, colunm_index, row_index,10,10);
            row_index += 10;
            if(row_index >= 20) {
                row_index = 2;
                colunm_index += 10;
            }
        }

        Label successInformation = new Label("");
        grid.add(successInformation, 0, 13, 10, 10);

        TextField shipname = new TextField();
        shipname.setPromptText("Enter your the ship type you want to buy");
        grid.add(shipname, 1, 30, 3, 3);

        TextField shiplocation = new TextField();
        shiplocation.setPromptText("Enter your the ship location you want to buy at");
        grid.add(shiplocation, 5, 30, 3, 3);
        Button button1 = new Button("purchase");
        Button button2 = new Button("back");
        grid.add(button1, 0, 30);
        grid.add(button2, 0, 35);
        button2.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(mainMenuText,token, argument);
            mainMenu.start(primaryStage);
        });

        Label info = new Label("");
        grid.add(info, 0, 36, 10, 10);
        button1.setOnAction(e -> {
            String shipname1 = shipname.getText();
            String shiplocation1 = shiplocation.getText();
            int shipPrice = getFromWeb.getShipPrice(token, shipname1, shiplocation1, argument);
            System.out.println(shipPrice);
            int loan = getFromWeb.GetExactAmountUserHas(token,argument);
            System.out.println(loan);

            if(shipPrice == -1) {
                info.setText("Ship not found");
            }
            else if (shipPrice == 0) {
                info.setText("Ship location not found");
            }

            else if (shipPrice <= loan) {
                info.setText("Successfully purchased " + shipname1);
                PurchaseresultPage purchaseresultPage = new PurchaseresultPage(token, shiplocation1, shipname1,mainMenuText, argument);
                purchaseresultPage.start(primaryStage);
            }else {
                info.setText("You do not have enough money to purchase this ship");
            }
        });
        Scene scene = new Scene(grid, 2000, 800);
        return scene;
    }

}

