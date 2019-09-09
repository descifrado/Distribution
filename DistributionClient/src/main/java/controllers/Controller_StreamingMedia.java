package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import data.Peer;
import data.SearchFile;
import fileHandler.FileReciever;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import mainApp.App;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import request.AvailablePieceRequest;
import request.FileDownloadRequest;
import request.PeerListRequest;
import request.Response;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Controller_StreamingMedia {

    public MediaView mediaView;
    public JFXButton back;
    public JFXButton play;
    public JFXSlider timeSlider;
    public JFXSlider volumeSlider;
    private MediaPlayer mediaPlayer;
    private boolean atEndOfMedia=false;
    private SearchFile currentSelectedFile;

    private void playNext(Queue<String> queue){
        if (queue.isEmpty())return;
        File mediaFile = new File(queue.peek());
        String uri = mediaFile.toURI().toString();
        Media media = new Media(uri);
        mediaPlayer=new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                String currentPiece=queue.poll();
                String home=System.getProperty("user.home");
                File file=new File(home+"/Downloads/"+currentSelectedFile.getFileUID()+"/"+currentPiece);
                file.delete();
                playNext(queue);
            }
        });
    }

    public List<Peer> getPeers(){
        List<Peer> peers=new ArrayList<>();
        PeerListRequest peerListRequest=new PeerListRequest(Controller_SearchFile.getCurrentSelectedFile());
        try {
            App.oosTracker.writeObject(peerListRequest);
            App.oosTracker.flush();
            Response response = (Response)App.oisTracker.readObject();
            peers=(ArrayList<Peer>)response.getResponseObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return peers;
    }

    public void preProcessStreaming() throws IOException, JSONException {

        List<Peer> peersList=getPeers();
        currentSelectedFile=Controller_SearchFile.getCurrentSelectedFile();

        String fileUID = currentSelectedFile.getFileUID();
        String home=System.getProperty("user.home");
        String path=fileUID+"downloaded.json";
        path=home+"/Downloads/"+path;

        java.io.File mkFolder = new java.io.File(home+"/Downloads/" + currentSelectedFile.getFileUID());
        mkFolder.mkdir();
        java.io.File tmpfile = new java.io.File(path);

        FileDownloadRequest fileDownloadRequest = new FileDownloadRequest(currentSelectedFile,App.user.getUserUID());
        App.oosTracker.writeObject(fileDownloadRequest);
        App.oosTracker.flush();
        String pathJsonFiles = "./jsonFiles";
        java.io.File jsonFolder = new java.io.File(pathJsonFiles);
        if(!jsonFolder.exists()){
            jsonFolder.mkdir();
        }
        FileReciever fileReciever = new FileReciever();
        fileReciever.readFile(fileReciever.createSocketChannel(App.getServerSocketChannel()),fileUID,pathJsonFiles);
        JSONObject completePieceJSON = new JSONObject(new JSONTokener(new FileReader(pathJsonFiles + "/" + fileUID)));
        int totalPieces = completePieceJSON.length();
        int totalPeers = peersList.size();

        Map peersPiece=new HashMap<Integer,Socket>();
        for (Peer peer: peersList) {
            Socket socket = null;
            try {
                socket=new Socket(peer.getIp(),6963);
                AvailablePieceRequest availablePieceRequest=new AvailablePieceRequest(fileUID);
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(availablePieceRequest);
                oos.flush();
                Response response= (Response) ois.readObject();
                String pieces= (String) response.getResponseObject();
                JSONObject pieceList = new JSONObject(pieces);
                for (Iterator it = pieceList.keys(); it.hasNext(); ) {
                    String piece = (String) it.next();
                    peersPiece.put(piece,socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize(){

        try {
            preProcessStreaming();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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


        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                
            }
        });
//        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Integer>() {
//            @Override
//            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer t1) {
//                Integer i=t1;
//                timeSlider.setValue(i);
//                timeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
//            }
//        });
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
