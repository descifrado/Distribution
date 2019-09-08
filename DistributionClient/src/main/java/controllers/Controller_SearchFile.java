package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import constants.FileType;
import constants.ResponseCode;
import data.File;
import data.Peer;
import data.SearchFile;
import fileHandler.FileDownloadHandler;
import fileHandler.FileMerger;
import fileHandler.FileReciever;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainApp.App;
import mainApp.Handler;
import org.json.JSONObject;
import org.json.JSONTokener;

import request.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller_SearchFile {
    public JFXTextField searchbyname;
    public JFXTextField searchbytag;
    public JFXListView files;
    public JFXListView tags;
    public JFXComboBox<String> searchbytype;
    public JFXButton back;
    public JFXButton stream;

    private SearchFile currentSelectedFile;
    private List<String> currentTags;


    public static volatile JSONObject downloadedPieceJSON ;
    public static volatile JSONObject completePieceJSON;
    public static volatile FileOutputStream jsonwriter;
    public static volatile int totalPieces,downloadedPieces;
    public static int totalPeers;
    public static volatile boolean isDownloadComplete;
    public static String currentFile;
    public static String[] getNames(Class<? extends Enum<?>> e)
    {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }


    @FXML
    public void initialize()
    {
        currentTags=new ArrayList<String>();
        String[] fileTypes = getNames(FileType.class);
        searchbytype.getItems().addAll(fileTypes);
        currentSelectedFile=null;
        stream.setOpacity(0.0);
        stream.setDisable(true);
    }


    public void ondownloadclicked(ActionEvent actionEvent)
    {
        File file = currentSelectedFile;
        PeerListRequest peerListRequest = new PeerListRequest(file);
        try
        {
            App.oosTracker.writeObject(peerListRequest);
            App.oosTracker.flush();
            Response response = (Response)App.oisTracker.readObject();
            List<Peer> peersList = (ArrayList<Peer>) response.getResponseObject();
            String fileUID = currentSelectedFile.getFileUID();
            String home=System.getProperty("user.home");
            String path=fileUID+"downloaded.json";
            path=home+"/Downloads/"+path;
            java.io.File mkFolder = new java.io.File(home+"/Downloads/" + currentSelectedFile.getFileUID());
            mkFolder.mkdir();
            java.io.File tmpfile = new java.io.File(path);
            if(!tmpfile.exists())
            {
                tmpfile.createNewFile();
                downloadedPieceJSON = new JSONObject();
            }
            else
            {
                downloadedPieceJSON = new JSONObject(new JSONTokener(new FileReader(path)));
                System.out.println("Reading existing json file");
                downloadedPieceJSON = new JSONObject();
            }
            jsonwriter = new FileOutputStream(tmpfile,true);
            FileDownloadRequest fileDownloadRequest = new FileDownloadRequest(file,App.user.getUserUID());
            App.oosTracker.writeObject(fileDownloadRequest);
            App.oosTracker.flush();
            String pathJsonFiles = "./jsonFiles";
            java.io.File jsonFolder = new java.io.File(pathJsonFiles);
            if(!jsonFolder.exists()){
                jsonFolder.mkdir();
            }
            FileReciever fileReciever = new FileReciever();
            fileReciever.readFile(fileReciever.createSocketChannel(App.getServerSocketChannel()),fileUID,pathJsonFiles);
            completePieceJSON = new JSONObject(new JSONTokener(new FileReader(pathJsonFiles+"/"+fileUID)));
            totalPieces = completePieceJSON.length();
            totalPeers=peersList.size();
            for(Peer peer : peersList){
                System.out.println(peer);
                new Thread(new FileDownloadHandler(peer,file)).start();
            }
            new Thread(() -> {
                while(downloadedPieces<totalPieces){
                    downloadedPieces = downloadedPieceJSON.length();
                    System.out.println("Pieces Downloaded : "+downloadedPieces );
                    System.out.println("Total Pieces : "+totalPieces);
                    System.out.println();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                isDownloadComplete = true;
                System.out.println("Download Complete");
                System.out.println("Now Merging Files .. !!");
                FileMerger fileMerger = new FileMerger();
                java.io.File mergedfile = new java.io.File(home+"/Downloads/"+currentSelectedFile.getFileName());
                try {
                    if (!mergedfile.exists()) {
                        mergedfile.createNewFile();
                    }
                    fileMerger.mergeFiles(home + "/Downloads/" + currentSelectedFile.getFileUID(), mergedfile);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }).start();
            currentFile = currentSelectedFile.getFileName();
            UpdateUserHistoryRequest updateUserHistoryRequest=new UpdateUserHistoryRequest(App.user.getUserUID(),fileUID,"1","0");
            try
            {
                App.sockerTracker = new Socket(App.serverIP,App.portNo);
                App.oosTracker = new ObjectOutputStream(App.sockerTracker.getOutputStream());
                App.oisTracker = new ObjectInputStream(App.sockerTracker.getInputStream());
                App.oosTracker.writeObject(updateUserHistoryRequest);
                App.oosTracker.flush();
                Response response1 = (Response)App.oisTracker.readObject();
                if(response1.getResponseCode()==ResponseCode.SUCCESS)
                {
                    System.out.println("User History Updated Successfully");
                }
                else
                {
                    System.out.println("Error in User History Update");
                }
            }
            catch (IOException | ClassNotFoundException e)
            {

                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("Failed Fetching of Files");
            }

            System.out.println("Hello");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Stage primaryStage = (Stage) searchbyname.getScene().getWindow();
                    Parent root = null;
                    try {

                        root = FXMLLoader.load(getClass().getResource("/downloadTracker.fxml"));
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    primaryStage.setScene(new Scene(root, 1081, 826));

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onsearchclicked(ActionEvent actionEvent) {
        currentSelectedFile=null;
        String nameString=searchbyname.getText();
        String typeString=searchbytype.getSelectionModel().getSelectedItem();
        FileType fileType;
        if (typeString==null)fileType=FileType.ALL;
        else fileType=FileType.valueOf(typeString);
        SearchRequest searchRequest;
        if(currentTags.isEmpty())
            searchRequest = new SearchRequest(nameString,fileType,null);
        else
            searchRequest = new SearchRequest(nameString,fileType,currentTags);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    App.oosTracker.writeObject(searchRequest);
                    App.oosTracker.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Response response = null;
                try {
                    response = (Response)App.oisTracker.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                List<SearchFile> availableFiles= (List<SearchFile>) response.getResponseObject();
                System.out.println(availableFiles);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        files.getItems().clear();
                        files.getItems().setAll(availableFiles);
                        searchbyname.setText("");
                        searchbytag.setText("");
                        searchbytype.valueProperty().set(null);
                        tags.getItems().clear();
                        currentTags.clear();

                    }
                });

            }
        }).start();

        stream.setOpacity(0.0);
        stream.setDisable(true);


    }

    public void onaddtagclicked(ActionEvent actionEvent) {
        String currentTag=searchbytag.getText();
        currentTags.add(currentTag);
        tags.getItems().clear();
        tags.getItems().addAll(currentTags);
        searchbytag.setText("");
    }

    public void onTagsListClicked(MouseEvent mouseEvent) {
        currentTags.remove(tags.getSelectionModel().getSelectedItems().get(0).toString());
        //     System.out.println(Arrays.toString(currentTags.toArray()));
        tags.getItems().clear();
        tags.getItems().addAll(currentTags);
        searchbytag.setText("");

    }


    public void onbackclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1303, 961));


            }
        });
    }

    public void onFilesListClicked(MouseEvent mouseEvent) {

        currentSelectedFile= (SearchFile) files.getSelectionModel().getSelectedItem();
        if (currentSelectedFile.getType().equals(FileType.MEDIA) || currentSelectedFile.getType().equals(FileType.AUDIO)){
            stream.setOpacity(1.0);
            stream.setDisable(false);
        }
        else {
            stream.setOpacity(0.0);
            stream.setDisable(true);
        }

    }

    public void onstreamclicked(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage primaryStage = (Stage) back.getScene().getWindow();
                Parent root = null;
                try {

                    root = FXMLLoader.load(getClass().getResource("/streamingMedia.fxml"));
                }catch(IOException e){
                    e.printStackTrace();
                }
                primaryStage.setScene(new Scene(root, 1303, 961));
            }
        });
    }
}
