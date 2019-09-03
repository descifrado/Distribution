package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainApp.App;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Controller_UploadFile
{
    @FXML
    public JFXTextField filePath;
    @FXML
    public JFXButton browse;
    Desktop desktop=Desktop.getDesktop();

    public void onbrowseclicked()
    {
        Stage stage = (Stage) browse.getScene().getWindow();
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            filePath.setText(file.getAbsolutePath());
        }
    }
    public void onuploadclicked()
    {

    }


    private void openFile(File file)
    {
        try
        {
            desktop.open(file);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

}
