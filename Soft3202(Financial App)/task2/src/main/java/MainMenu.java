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

public class MainMenu extends Application {
    GetFromWeb getFromWeb = new GetFromWeb();
    private String text;
    private String token;
    private String argument;
    public MainMenu(String text, String token, String argument) {
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

        Text scenetitle = new Text("Welcome to Main Page! \nWhat would you like to do?");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button button1 = new Button("View Obtain Loan");
        Button button2 = new Button("Purchase Ships (you can see all ships yoou can buy here)");
        Button button3 = new Button("Take out a Loan");
        Button button4 = new Button("Purchase Goods");
        Button button5 = new Button("View Market");
        Button button6 = new Button("View Ship");
        Button button7 = new Button("Near by Locations");
        Button button8 = new Button("Create Flight Plan");
        Button button9 = new Button("View Flight Plan");
        Button button10 = new Button("View Ship's Cargo");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);
        grid.add(button1, 2, 1);
        grid.add(button2, 2, 2);
        grid.add(button3, 2, 3);
        grid.add(button4, 2, 4);
        grid.add(button5, 2, 5);
        grid.add(button6, 2, 6);
        grid.add(button7, 2, 7);
        grid.add(button8, 2, 8);
        grid.add(button9, 2, 9);
        grid.add(button10, 2, 10);



        Label label = new Label(text);
        grid.add(label, 0, 5, 10, 10);

        button1.setOnAction((event) -> {
            ShowLoans showShips = new ShowLoans(token, text, argument);
            showShips.start(primaryStage);
        });

        button2.setOnAction((event) -> {
            ShowShips showShips = new ShowShips(token,text,argument);
            showShips.start(primaryStage);
        });

        button3.setOnAction((event) -> {
            TakeOutLoanPage takeOutLoanPage = new TakeOutLoanPage(token,text,argument);
        takeOutLoanPage.start(primaryStage);
        });

        button4.setOnAction((event) -> {
            PurchaseFuelPage purchasereFuelPage = new PurchaseFuelPage(token,text,argument);
            purchasereFuelPage.start(primaryStage);
        });

        button5.setOnAction((event) -> {
            MarketPlacePage marketPlacePage = new MarketPlacePage(token,text,argument);
            marketPlacePage.start(primaryStage);
        });

        button6.setOnAction((event) -> {
            ListAllShips listAllShips = new ListAllShips(token,text,argument);
            listAllShips.start(primaryStage);
        });

        button7.setOnAction((event) -> {
            ListNearBYLocations nearByLocations = new ListNearBYLocations(token,text,argument);
            nearByLocations.start(primaryStage);
        });

        button8.setOnAction((event) -> {
            CreateFlightPlan createFlightPlan = new CreateFlightPlan(token,text,argument);
            createFlightPlan.start(primaryStage);
        });

        button9.setOnAction((event) -> {
            ViewFlightPlan viewFlightPlan = new ViewFlightPlan(token,text,argument);
            viewFlightPlan.start(primaryStage);
                });

        button10.setOnAction((event) -> {
            SellGoods sellGoods = new SellGoods(token,text,argument);
            sellGoods.start(primaryStage);
        });




        Scene scene = new Scene(grid, 800, 800);
        return scene;
    }
}
