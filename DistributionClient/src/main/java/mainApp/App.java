package mainApp;

import data.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * JavaFX App
 */
public class App extends Application {

    public static String serverIP = "192.168.0.110";
    public static  int portNo = 6963;
    public static Socket sockerTracker ;
    public static ObjectOutputStream oosTracker ;
    public static ObjectInputStream oisTracker;
    public static User user;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        primaryStage.setTitle("Distribution");
        primaryStage.setScene(new Scene(root, 1081, 826));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}