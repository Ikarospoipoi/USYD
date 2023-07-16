package au.edu.sydney.soft3202.task3;

import au.edu.sydney.soft3202.task3.database.Database;
import au.edu.sydney.soft3202.task3.model.GameBoard;
import au.edu.sydney.soft3202.task3.view.GameWindow;
import au.edu.sydney.soft3202.task3.view.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import static au.edu.sydney.soft3202.task3.database.Database.*;

public class Main extends Application {
    private Database database = new Database();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        removeDB();
        createDB();
        setupDB();
        LoginWindow loginWindow = new LoginWindow(primaryStage);
        primaryStage.setScene(loginWindow.LoginScene());
//        primaryStage.show();
//        primaryStage.setScene(loginWindow.getScene());
//        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("Checkers");
        primaryStage.show();
    }
}
