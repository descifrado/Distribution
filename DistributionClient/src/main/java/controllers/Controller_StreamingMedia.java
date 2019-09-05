package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_StreamingMedia implements Initializable
{
    @FXML
    private MediaView mediaView;
    @FXML
    private JFXButton play;
    @FXML
    private JFXButton stop;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider timeSlider;
    @FXML
    private Pane mediaPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}
