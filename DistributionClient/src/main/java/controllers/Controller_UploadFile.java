package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainApp.App;
import tools.GetFileType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Controller_UploadFile
{
    @FXML
    public JFXTextField filePath,tags;
    @FXML
    public JFXButton browse,upload,more;
    private data.File myfile=new data.File(null,null,null,null);
    private Set<String> tagSet=new HashSet<String>();
    private String path,fileName;

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
    public void onmoreclicked()
    {
        if(!tags.getText().isEmpty())
            tagSet.add(tags.getText());
        tags.setText("");
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
        }
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
