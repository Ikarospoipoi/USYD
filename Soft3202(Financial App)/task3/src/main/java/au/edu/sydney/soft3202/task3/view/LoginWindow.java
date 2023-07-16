package au.edu.sydney.soft3202.task3.view;

import au.edu.sydney.soft3202.task3.database.Database;
import au.edu.sydney.soft3202.task3.model.GameBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow {
    private Scene scene;
    private int a =1;
    private Stage stage;

    public LoginWindow(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public Scene LoginScene() {
        GridPane grid = new GridPane();

//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setPromptText("Enter your username");
        grid.add(userTextField, 1, 1);

        Button button1 = new Button("Log in");
        grid.add(button1, 2, 1);


        Label label = new Label("");
        grid.add(label, 0, 3, 5, 10);



        button1.setOnAction((event) -> {
            String name = userName.getText();
            Database.addUser(name);
            System.out.println("User added");
            GameBoard gameBoard = new GameBoard(name);
            GameWindow gameWindow = new GameWindow(gameBoard,name);
            gameWindow.start(stage);
//            scene = gameWindow.getScene();
//            a=2;
        });
        scene = new Scene(grid, 500, 300);
        return scene;
    }

}
