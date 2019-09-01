package mainApp;
import com.jfoenix.controls.JFXTextField;
import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainApp.App;

import java.io.IOException;

public class Controller_Dashboard
{
    @FXML
    public JFXTextField name;
    public void initialize(){
        name.setText(App.user.getFirstName());
    }


    public void onshareclicked(ActionEvent actionEvent) {
    }

    public void ondownloadclicked(ActionEvent actionEvent) {
    }

    public void onlogoutclicked(ActionEvent actionEvent) {
        try{
            App.sockerTracker.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) name.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/login.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1081, 826));

            }
        });

    }
}
