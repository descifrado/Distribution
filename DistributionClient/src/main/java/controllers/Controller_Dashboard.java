package controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import constants.ResponseCode;
import data.File;
import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainApp.App;
import request.DownloadedFileRequest;
import request.Response;
import request.SharedFileRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

public class Controller_Dashboard
{

    private String userUID;
    @FXML
    public JFXListView downloadedfiles,sharedfiles;
    public JFXTextField firstname,lastname,email,phone;
    public JFXButton sharefile,download,logout;
    private List<File> files;
    public void initialize(){
        firstname.setText(App.user.getFirstName());
        lastname.setText(App.user.getLastName());
        email.setText(App.user.getEmail());
        phone.setText(App.user.getPhone());
        userUID=App.user.getUserUID();
        DownloadedFileRequest downloadedFileRequest=new DownloadedFileRequest(userUID);
        try
        {
            App.sockerTracker = new Socket(App.serverIP,App.portNo);
            App.oosTracker = new ObjectOutputStream(App.sockerTracker.getOutputStream());
            App.oisTracker = new ObjectInputStream(App.sockerTracker.getInputStream());
            App.oosTracker.writeObject(downloadedFileRequest);
            App.oosTracker.flush();Response response;
            response = (Response)App.oisTracker.readObject();

            List<File> files=(List<File>) response.getResponseObject();
            if(response.getResponseCode().equals(ResponseCode.SUCCESS))
            {
                for(File file: files)
                {
                    downloadedfiles.getItems().add(file.getFileName());
                }
            }
            else
            {
                downloadedfiles.getItems().add("Empty");
                System.out.println("Failed Fetching of Files");
            }

        }
        catch (IOException | ClassNotFoundException e)
        {
            downloadedfiles.getItems().add("Empty");
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Failed Fetching of Files");
        }
        SharedFileRequest sharedFileRequest=new SharedFileRequest(userUID);
        try
        {
            App.sockerTracker = new Socket(App.serverIP,App.portNo);
            App.oosTracker = new ObjectOutputStream(App.sockerTracker.getOutputStream());
            App.oisTracker = new ObjectInputStream(App.sockerTracker.getInputStream());
            App.oosTracker.writeObject(sharedFileRequest);
            App.oosTracker.flush();Response response;
            response = (Response)App.oisTracker.readObject();

            files=(List<File>) response.getResponseObject();
            if(response.getResponseCode().equals(ResponseCode.SUCCESS))
            {
                for(File file: files)
                {
                    sharedfiles.getItems().add(file.getFileName());
                }
            }
            else
            {
                sharedfiles.getItems().add("Empty");
                System.out.println("Failed Fetching of Files");
            }

        }
        catch (IOException | ClassNotFoundException e)
        {
            downloadedfiles.getItems().add("Empty");
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Failed Fetching of Files");
        }
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

    public void onfiledclicked(MouseEvent mouseEvent)
    {
        int idx=downloadedfiles.getSelectionModel().getSelectedIndex();
        File f=(File)files.get(idx);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"File Name: "+f.getFileName()+"\nFile Type: "+f.getType()+"\nTags: "+f.getTags());
        alert.showAndWait();
    }

    public void onfilesclicked(MouseEvent mouseEvent)
    {
        int idx=sharedfiles.getSelectionModel().getSelectedIndex();
        File f=(File)files.get(idx);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"File Name: "+f.getFileName()+"\nFile Type: "+f.getType()+"\nTags: "+f.getTags());
        alert.showAndWait();
    }
}
