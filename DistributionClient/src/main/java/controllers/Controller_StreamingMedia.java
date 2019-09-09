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
import request.*;

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
    private Map peersPiece;
    private Map peeroos,peerois,socketpeers;

    private void playNext(Queue<String> queue){
        if (queue.isEmpty())return;
        File mediaFile = new File(queue.peek());
        String uri = mediaFile.toURI().toString();
        while (!mediaFile.exists()){}
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

        peersPiece=new HashMap<String,String>();
        peeroos = new HashMap<String,ObjectOutputStream>();
        peerois = new HashMap<String,ObjectInputStream>();
        socketpeers = new HashMap<String,Socket>();
        for(Peer peer:peersList){
            socketpeers.put(peer.getIp(),new Socket(peer.getIp(),6963));
            Socket socket = (Socket) socketpeers.get(peer.getIp());
            peeroos.put(peer.getIp(),new ObjectOutputStream(socket.getOutputStream()));
            peerois.put(peer.getIp(),new ObjectInputStream(socket.getInputStream()));
        }
        for (Peer peer: peersList) {

            try {

                AvailablePieceRequest availablePieceRequest=new AvailablePieceRequest(fileUID);
                ObjectInputStream ois=(ObjectInputStream) peerois.get(peer.getIp());
                ObjectOutputStream oos=(ObjectOutputStream)peeroos.get(peer.getIp());
                oos.writeObject(availablePieceRequest);
                oos.flush();
                Response response= (Response) ois.readObject();
                String pieces= (String) response.getResponseObject();
                JSONObject pieceList = new JSONObject(pieces);
                for (Iterator it = pieceList.keys(); it.hasNext(); ) {
                    String piece = (String) it.next();
                    peersPiece.put(piece,peer.getIp());
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

    public void startPlayer(Queue<String> paths){

        String home=System.getProperty("user.home");
        String pathFolder = home+"/Downloads/" + currentSelectedFile.getFileUID();

        new Thread(() -> {
            int partCounter=1;
            while(true){
                String current=String.format("%05d",partCounter++);
                String peerIp = (String) peersPiece.get(current);
                Socket s = (Socket)socketpeers.get(peerIp);
                PieceDownloadRequest pieceDownloadRequest=new PieceDownloadRequest(currentSelectedFile,current);
                try {
                    ObjectOutputStream oos= (ObjectOutputStream) peeroos.get(peerIp);
                    oos.writeObject(pieceDownloadRequest);
                    oos.flush();
                    FileReciever fileReciever =  new FileReciever();
                    fileReciever.readFile(fileReciever.createSocketChannel(App.getServerSocketChannel()),current,pathFolder);
                    Process p =Runtime.getRuntime().exec("ffmpeg -i "+current + ".ts " + current+".mp4");
                    while (p.isAlive()){
                        System.out.println("...");
                        System.out.println("...");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                paths.add(pathFolder+"/"+partCounter+".mp4");
            }
        }).start();

    }
    public void initialize(){

        try {
            System.out.println("Chal Jaa ehle!!!");
            preProcessStreaming();
            System.out.println("Chal Jaa!!!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String home=System.getProperty("user.home");
        String pathFolder = home+"/Downloads/" + currentSelectedFile.getFileUID();
        Queue<String> paths=new LinkedList<>();
        startPlayer(paths);

        while (paths.isEmpty()){}
        String path=paths.peek();

        File mediaFile = new File(path);
        while (!mediaFile.exists()){}
        String uri = mediaFile.toURI().toString();
        Media media = new Media(uri);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        timeSlider.setValue(0.0);

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                timeSlider.setValue(newValue.toSeconds());
                timeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
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
