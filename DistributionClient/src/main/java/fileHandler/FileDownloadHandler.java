package fileHandler;

import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FileDownloadHandler implements Runnable {
    String peerIP;
    public FileDownloadHandler(String peerIP){
        this.peerIP = peerIP;
    }

    JSONObject availablePieces;
    Socket peerSocket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    @Override
    public void run() {
        try{
            peerSocket = new Socket(this.peerIP,6963);
        }catch (Exception e){

        }
    }
}
