package controllers;
/**
 *  Ui based class for showing the file being downloaded
 *  Total number of chunks and the number of chunks downloaded
 *  Total number of peers from which parallel downloading is going on
 *  Progress bar for showing the progress of the file downloading
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_DownloadTracker {
    
    public JFXTextField pieces;
    public JFXTextField peers;
    public JFXButton back;
    public JFXTextField file;
    public ProgressBar progressbar;

    public void initialize(){
        file.setText(Controller_SearchFile.currentFile);
        peers.setText(String.valueOf(Controller_SearchFile.totalPeers));
        new Thread(() -> {
            while (Controller_SearchFile.downloadedPieces<=Controller_SearchFile.totalPieces) {
                double progress = (double) Controller_SearchFile.downloadedPieces / Controller_SearchFile.totalPieces;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    pieces.setText(String.valueOf(Controller_SearchFile.downloadedPieces));
                    progressbar.setProgress(progress);

                });
            }
        }).start();

    }
    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
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
}
