package milestone2.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import milestone2.model.GetFromWeb;
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

import java.io.File;

import static milestone2.model.Database.createDB;
import static milestone2.model.Database.setupDB;

public class LoginPage extends Application {
    public static final String SPLASH_IMAGE =
            "http://fxexperience.com/wp-content/uploads/2010/06/logo.png";
    GetFromWeb getFromWeb = new GetFromWeb();
    public static MediaPlayer mediaPlayer;
    private static String argument;
    private static String argument_pastebin;
    private CompanyPojo Saved_CompanyPojo;
    private String result;
    public String flag;
    private String type;

    public static void main(String[] args) {
        argument = args[0];
        argument_pastebin = args[1];
        
        launch(args);
    }

    /**
     * This method is used to pause the music
     */
    public void pause_music() {
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }else{
            mediaPlayer.play();
        }
    }

    /**
     * This method is used to plau the music
     */
    private static void playMusic(){
        String musicFile = "src/main/resources/Jazz.wav";
        Media sound = new Media(new File(musicFile).toURI().toString());
        System.out.println(sound.getSource());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        mediaPlayer.play();
    }

    public void start(Stage primaryStage) {
        // Create a label
        playMusic();
        primaryStage.setTitle("Alpha Vantage");

        Scene scene = loginScence(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    /**
     * This method is used to create the login scene
     * @param primaryStage
     * @return
     */
    public Scene loginScence(Stage primaryStage) {
        createDB();
        setupDB();
        Scene scene;

        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

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
        Button button3 = new Button();
        button3.setGraphic(view);
        button3.setOnAction((event) -> {
            pause_music();
        });
        HBox hBox = new HBox();
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        HBox.setHgrow(button3, Priority.NEVER);
        hBox.getChildren().add(menuBar);
        hBox.getChildren().add(button3);

        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);


        Text scenetitle = new Text("Welcome to Alpha Vantage");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button button2 = new Button("click to continue");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(button1);
        grid.add(button2, 0, 1);

        button2.setOnAction((event) -> {

            MainMenu mainMenu = new MainMenu(argument, argument_pastebin,Saved_CompanyPojo,result,flag,type);
            mainMenu.start(primaryStage);
        });

        VBox.setVgrow(grid, Priority.ALWAYS);
        vBox.getChildren().add(grid);
        scene = new Scene(vBox, 300, 200);
        return scene;
    }
}

