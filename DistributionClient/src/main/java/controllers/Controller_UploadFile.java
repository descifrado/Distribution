package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.tools.javac.Main;
import constants.ResponseCode;
import fileHandler.FileSender;
import fileLoader.PieceGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainApp.App;
import org.json.JSONObject;
import request.FileCheckRequest;
import request.FileUploadRequest;
import request.Response;
import request.SignUpRequest;
import tools.GetFileType;
import tools.HashGenerator;
import tools.UIDGenerator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Controller_UploadFile
{
    @FXML
    public JFXTextField filePath,tags;
    @FXML
    public JFXButton browse,upload,more,back;
    @FXML
    Label status;
    private data.File myfile=new data.File(null,null,null,null);
    private Set<String> tagSet=new HashSet<String>();
    private String path,fileName;
    private File file;

    Desktop desktop=Desktop.getDesktop();

    public void onbrowseclicked()
    {
        Stage stage = (Stage) browse.getScene().getWindow();
        FileChooser fileChooser=new FileChooser();
        file=fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            filePath.setText(file.getAbsolutePath());
        }
    }
    public void onmoreclicked()
    {
        if(!tags.getText().isEmpty())
        {
            //System.out.println(tags.getText());
            tagSet.add(tags.getText());
        }
        tags.clear();

    }
    public void onuploadclicked()
    {
        if(!filePath.getText().isEmpty())
        {
            path=filePath.getText();
            String pathArray[]=path.split("/");
            fileName=pathArray[pathArray.length-1];
            myfile.setFileName(fileName);
            myfile.setType(GetFileType.getFileType(path));
            myfile.setTags(tagSet);
            JSONObject fileJSON=PieceGenerator.getJSON(file);
            myfile.setFileUID(UIDGenerator.generateuid(PieceGenerator.generateJSON(fileJSON,file)));

            String home=System.getProperty("user.home");
            String pathdownloaded=myfile.getFileUID()+"downloaded.json";
            pathdownloaded=home+"/Downloads/"+pathdownloaded;
            java.io.File mkFolder = new java.io.File(home+"/Downloads/" + myfile.getFileUID());
            mkFolder.mkdir();
            java.io.File tmpfile = new java.io.File(pathdownloaded);


            try{
                if(!tmpfile.exists())
                    tmpfile.createNewFile();
                if(App.sockerTracker == null){
                    App.sockerTracker = new Socket(App.serverIP, App.portNo);
                    App.oosTracker = new ObjectOutputStream(App.sockerTracker.getOutputStream());
                    App.oisTracker = new ObjectInputStream(App.sockerTracker.getInputStream());
                }
                FileCheckRequest fileCheckRequest =  new FileCheckRequest(myfile, App.user.getUserUID(), InetAddress.getLocalHost().getHostAddress());
                App.oosTracker.writeObject(fileCheckRequest);
                App.oosTracker.flush();
                Response fileCheckResponse = (Response)App.oisTracker.readObject();
                if(fileCheckResponse.getResponseCode().equals(ResponseCode.FAILED)){
                    status.setText("Upload Successful(Exists)");
                    return;
                }



                FileUploadRequest fileUploadRequest = new FileUploadRequest(myfile,App.user.getUserUID(),InetAddress.getLocalHost().getHostAddress());
                App.oosTracker.writeObject(fileUploadRequest);
                App.oosTracker.flush();
                FileSender fileSender=new FileSender();
                fileSender.sendFile(fileSender.createSocketChannel(),path+".json");
                Response response = (Response)App.oisTracker.readObject();
                if(response.getResponseCode().equals(ResponseCode.SUCCESS)){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("Upload Successful");
                        }
                    });
                }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("Error");
                        }
                    });
                }

                Files.copy(new File(path+".json").toPath(),tmpfile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }

    public void onbackclicked()
    {
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


}
