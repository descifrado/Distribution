package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javafx.util.Duration;
import request.AvailablePieceRequest;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Controller_StreamingMedia {

    public MediaView mediaView;
    public JFXButton back;
    public JFXButton play;
    public JFXSlider timeSlider;
    public JFXSlider volumeSlider;
    private MediaPlayer mediaPlayer;
    private boolean atEndOfMedia=false;

    private void playNext(Queue<String> queue){
        if (queue.isEmpty())return;
        File mediaFile = new File(queue.poll());
        String uri = mediaFile.toURI().toString();
        Media media = new Media(uri);
        mediaPlayer=new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playNext(queue);
            }
        });
    }
    public void initialize(){
//        AvailablePieceRequest availablePieceRequest=new AvailablePieceRequest();
        String path="/home/moonknight/Desktop/teet/split.1.ts.mp4";
        File mediaFile = new File(path);
        String uri = mediaFile.toURI().toString();
        Media media = new Media(uri);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        timeSlider.setValue(0.0);

        Queue<String> paths=new LinkedList<>();
        for (int i = 2; i < 20; i++) {
            paths.add("/home/moonknight/Desktop/teet/split."+i+".ts.mp4");
        }
        timeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
            }
        });

        timeSlider.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
            }
        });
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playNext(paths);
            }
        });
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });

    }
    public void onbackclicked(ActionEvent actionEvent) {
        mediaPlayer.stop();
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

    public void PLAY(ActionEvent actionEvent) {
        mediaPlayer.play();
    }

    public void STOP(ActionEvent actionEvent) {
        mediaPlayer.stop();
    }

    public void PAUSE(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }
    public void onSeekVideo(MouseEvent mouseEvent) {
        Duration duration=mediaPlayer.getTotalDuration();
    //    mediaPlayer.seek(duration.multiply(timeSlider.getValue()/100.0));
    }

}
