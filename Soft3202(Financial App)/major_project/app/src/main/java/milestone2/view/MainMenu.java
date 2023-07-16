package milestone2.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import milestone2.model.Database;
import milestone2.model.GetFromWeb;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static milestone2.view.LoginPage.mediaPlayer;

public class MainMenu extends Application {
    Database database = new Database();
    private String argument;
    private String argument_pastebin;
    ProgressIndicator progressIndicator = new ProgressIndicator();
    private CompanyPojo Saved_CompanyPojo;
    private String Current_Company;
    private String flag;
    private String type;
    public MainMenu(String argument, String argument_pastebin, CompanyPojo Saved_CompanyPojo, String Current_Company, String flag, String type) {
        this.argument = argument;
        this.argument_pastebin = argument_pastebin;
        this.Saved_CompanyPojo = Saved_CompanyPojo;
        this.Current_Company = Current_Company;
        this.flag = flag;
        this.type = type;
    }

    public void start(Stage primaryStage) {
        // Create a label
        primaryStage.setTitle("Welcome to Search Page!");

        Scene scene = MianScence(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create a scene for the main menu
     * @param primaryStage
     * @return scene
     */
    public Scene MianScence(Stage primaryStage) {
        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Menu menu = new Menu("Menu");
        MenuItem menuItem1 = new MenuItem("About");
        menuItem1.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("About");
            alert.setContentText("Author Name: Frank\nApplication Name: Alpha Vantage\nVersion: 1.0\nReference:https://stackoverflow.com/questions/52045233/adding-menubar-to-gridpane\nhttp://www.javafxchina.net/blog/2015/04/doc03_linechart/");
            alert.showAndWait();
        });

        menu.getItems().add(menuItem1);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        Image img = new Image("pause.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        Button pause_button = new Button();
        pause_button.setGraphic(view);

        pause_button.setOnAction(e -> {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }else{
                mediaPlayer.play();
            }
        });

        HBox hBox = new HBox();
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        HBox.setHgrow(pause_button, Priority.NEVER);
        hBox.getChildren().add(menuBar);
        hBox.getChildren().add(pause_button);

        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.TOP_CENTER);
        hBox1.setSpacing(10);
        VBox vBox1 = new VBox();
        vBox1.setSpacing(10);
        vBox1.setAlignment(Pos.TOP_CENTER);

        Text scenetitle = new Text("              Welcome to Search Page! \nEnter the company name you want to search");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.BLACK);
        vBox1.getChildren().add(scenetitle);


        TextField company_name_text = new TextField();
        vBox1.getChildren().add(company_name_text);

        Button search_button = new Button("Search");
        vBox1.getChildren().add(search_button);


        hBox1.getChildren().add(vBox1);
        Label saved_company = new Label(Current_Company);
        saved_company.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        hBox1.getChildren().add(saved_company);
        grid.add(hBox1, 0, 0);

        Button btn9 = new Button("Delete Cache");
        btn9.setOnAction(e -> {
            database.delete_cache();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Cache");
            alert.setHeaderText("Delete Cache");
            ButtonType okButton = new ButtonType("Back", ButtonBar.ButtonData.YES);
            alert.showAndWait();
            if(alert.getResult() == okButton) {
                alert.close();
            }
        });
        vBox1.getChildren().add(btn9);
        progressIndicator.setVisible(false);
        vBox1.getChildren().add(progressIndicator);
        search_button.setOnAction(e -> {
            String text = company_name_text.getText();
            Mytask mytask = new Mytask(argument,argument_pastebin,grid,primaryStage,text,progressIndicator,hBox1,search_button,Saved_CompanyPojo,Current_Company,flag,type);
            Thread thread = new Thread(mytask);
            thread.start();
            });

        VBox.setVgrow(grid, Priority.ALWAYS);
        vBox.getChildren().add(grid);
        Scene scene = new Scene(vBox, 600, 600);

        return scene;
    }
}



class Mytask extends Task<Void> {
    private String argument;
    private String argument_pastebin;
    private GridPane grid;
    private Stage primaryStage;
    private GetFromWeb getFromWeb = new GetFromWeb();
    private String result;
    private String text;
    private ProgressIndicator progressIndicator;
    private HBox hBox;
    private Button search_button;
    private CompanyPojo Saved_CompanyPojo;
    private String current_company;
    private String flag;
    private String type;


    public Mytask(String argument, String argument_pastebin, GridPane grid, Stage primaryStage, String text, ProgressIndicator progressIndicator, HBox hBox, Button search_button, CompanyPojo companyPojo, String current_company, String flag, String type) {
        this.argument = argument;
        this.grid = grid;
        this.primaryStage = primaryStage;
        this.text = text;
        this.progressIndicator = progressIndicator;
        this.hBox = hBox;
        this.search_button = search_button;
        this.argument_pastebin = argument_pastebin;
        this.Saved_CompanyPojo = companyPojo;
        this.current_company = current_company;
        this.flag = flag;
        this.type = type;
    }

    /**
     * Creates a new thread to run the task when search company
     * @return
     * @throws Exception
     */
    @Override
    protected Void call() throws Exception {
        progressIndicator.setVisible(true);
        search_button.setDisable(true);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        if(text.equals("")) {
            text = "Arandomcompanythatusetohittheerror";
        }
        text = text.replace(" ","%20");
        result = getFromWeb.SearchCompany(text, argument);
        return null;
    }

    /**
     * This method is used to handle when the thread is finished
     * @return
     * @throws Exception
     */
    @Override
    protected void succeeded() {
        Platform.runLater(() -> {
            CompanyResult companyResult = new CompanyResult(argument,argument_pastebin,result,grid,hBox,Saved_CompanyPojo,current_company,flag,type);
            companyResult.addtoMain(primaryStage);
            progressIndicator.setVisible(false);
            search_button.setDisable(false);
        });
    }
}








