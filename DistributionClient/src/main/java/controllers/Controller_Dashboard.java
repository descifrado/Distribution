package controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainApp.App;

import java.io.IOException;

public class Controller_Dashboard
{
    @FXML
    public JFXTextField firstname,lastname,email,phone;
    public JFXButton sharefile,download,logout;
    public void initialize(){
        firstname.setText(App.user.getFirstName());
        lastname.setText(App.user.getLastName());
        email.setText(App.user.getEmail());
        phone.setText(App.user.getPhone());
    }


    public void onshareclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) sharefile.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/uploadFile.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1081, 826));

            }
        });
    }

    public void ondownloadclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) download.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/searchFile.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1081, 826));

            }
        });
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
                Stage primaryStage = (Stage) firstname.getScene().getWindow();
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
