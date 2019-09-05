package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import constants.FileType;
import data.SearchFile;
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
import request.Response;
import request.SearchRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller_SearchFile {
    public JFXTextField searchbyname;
    //public JFXTextField searchbytype;
    public JFXTextField searchbytag;
    public JFXListView files;
    public JFXListView tags;
    public JFXComboBox<String> searchbytype;



    private List<String> currentTags;

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
    @FXML
    public void initialize(){
        currentTags=new ArrayList<String>();
        String[] fileTypes = getNames(FileType.class);
        searchbytype.getItems().addAll(fileTypes);

    }
    public void ondownloadclicked(ActionEvent actionEvent) {

    }

    public void onsearchclicked(ActionEvent actionEvent) {
        String nameString=searchbyname.getText();
        String typeString=searchbytype.getSelectionModel().getSelectedItem();
        FileType fileType;
        if (typeString==null)fileType=FileType.ALL;
        else fileType=FileType.valueOf(typeString);

        SearchRequest searchRequest = new SearchRequest(nameString,fileType,currentTags);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    App.oosTracker.writeObject(new SearchRequest(nameString,fileType,currentTags));
                    App.oosTracker.flush();
                    System.out.print(searchRequest.getTags()+" ");
                    System.out.print(searchRequest.getName()+" ");
                    System.out.println(searchRequest.getType());
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
                //               System.out.println(availableFiles);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        files.getItems().clear();
                        files.getItems().addAll(availableFiles);
                        searchbyname.setText("");
                        searchbytag.setText("");
                        searchbytype.valueProperty().set(null);
                        tags.getItems().clear();
                        currentTags.clear();

                    }
                });
            }
        }).start();




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
}

