package fileHandler;

import constants.ResponseCode;
import controllers.Controller_SearchFile;
import data.File;
import data.Peer;
import mainApp.App;
import mainApp.Handler;
import org.json.JSONObject;
import request.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;

public class FileDownloadHandler implements Runnable {
    String peerIP;
    Peer peer;
    File file;
    public FileDownloadHandler(Peer peer, File file){
        this.peer = peer;
        this.peerIP = this.peer.getIp();
        this.file = file;
    }

    JSONObject availablePieces;
    Socket peerSocket;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    @Override
    public void run() {
        JSONObject tmp  = new JSONObject();
        try{

            peerSocket = new Socket(this.peerIP,6963);
            oos = new ObjectOutputStream(peerSocket.getOutputStream());
            ois = new ObjectInputStream(peerSocket.getInputStream());
            AvailablePieceRequest availablePieceRequest = new AvailablePieceRequest(file.getFileUID());
            oos.writeObject(availablePieceRequest);
            oos.flush();

            Response response = (Response)ois.readObject();
            String home=System.getProperty("user.home");
            String pathFolder = home+"/Downloads/" + file.getFileUID();
            String pathfile=file.getFileUID()+"downloaded.json";
            pathfile=home+"/Downloads/"+pathfile;

            if(response.getResponseCode().equals(ResponseCode.FAILED)){
                System.out.println("Some Error From Peer : "+peerIP);

            }else{
                //                send a request to add entry in peers table.
                File tmpfile = new File(Controller_SearchFile.currentSelectedFile.getFileUID(),Controller_SearchFile.currentSelectedFile.getFileName(),null,null);

                FileDownloadCompleteRequest fileDownloadCompleteRequest = new FileDownloadCompleteRequest(tmpfile,App.user.getUserUID(), InetAddress.getLocalHost().getHostAddress());
                App.oosTracker.writeObject(fileDownloadCompleteRequest);
                App.oosTracker.flush();
                Response responsepeerlist = (Response)App.oisTracker.readObject();

                if(responsepeerlist.getResponseCode().equals(ResponseCode.SUCCESS)){
                    System.out.println("Added User Entry To Peer Table");
                }else{
                    System.out.println("User Entry in Peer Table Failed");
                }


                String jsonString = (String)response.getResponseObject();
                availablePieces = new JSONObject(jsonString);
                Iterator<String> keys = availablePieces.keys();

                while(keys.hasNext()){
                    String  key = keys.next();
                    if(!Controller_SearchFile.downloadedPieceJSON.has(key)){
//                        send piece download request

                        PieceDownloadRequest pieceDownloadRequest = new PieceDownloadRequest(file,key);
                        oos.writeObject(pieceDownloadRequest);
                        oos.flush();
                        FileReciever fileReciever =  new FileReciever();
                        fileReciever.readFile(fileReciever.createSocketChannel(App.getServerSocketChannel()),key,pathFolder);
                        System.out.println("Peer: " + peerIP);
                        Controller_SearchFile.downloadedPieceJSON.put(key,availablePieces.get(key));

                        tmp.put(key,availablePieces.get(key));
                        FileWriter fileWriter =new FileWriter(Controller_SearchFile.downloadedpiecesFile,false);
                        fileWriter.write(tmp.toString());
                        fileWriter.flush();

                    }
                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
