package controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import constants.FileType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import request.SearchRequest;

import java.util.ArrayList;
import java.util.List;

public class Controller_SearchFile {
    public JFXTextField searchbyname;
    public JFXTextField searchbytype;
    public JFXTextField searchbytag;
    public JFXListView files;
    public JFXListView tags;


    private List<String> currentTags;

    @FXML
    public void initialize(){
        currentTags=new ArrayList<String>();
    }
    public void ondownloadclicked(ActionEvent actionEvent) {
        
    }

    public void onsearchclicked(ActionEvent actionEvent) {
        String nameSearchString;
        FileType typeSearchString;


    }

    public void onaddtagclicked(ActionEvent actionEvent) {
        String currentTag=searchbytag.getText();
        currentTags.add(currentTag);
        tags.getItems().addAll(currentTags);
        currentTags.clear();
        searchbytag.setText("");
    }
}
