package mainApp;

import constants.RequestCode;
import data.File;
import fileHandler.AvailablePieceHandler;
import fileHandler.FileSender;
import netscape.javascript.JSObject;
import request.AvailablePieceRequest;
import request.PieceDownloadRequest;
import request.Request;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class HandleClientRequest implements Runnable {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public HandleClientRequest(Socket socket) {
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    Request request = null;
    @Override
    public void run() {
        Request request = null;
        while(true)
        {
            try
            {
                try
                {
                    Object object=ois.readObject();
                    System.out.println(object.getClass());
                    request = (Request)object;
                    System.out.println(request);
                }catch (StreamCorruptedException e){
                    e.printStackTrace();
                    return;
                }
                catch (EOFException e)
                {
                    System.out.println("Client Disconnected..!!");
                    return;
                }
                catch (SocketException e)
                {
                    System.out.println("Client Disconnected..!!");
                    return;
                }
                if(request.getRequestCode().equals(RequestCode.AVAILABLEPIECE_REQUEST))
                {

                    AvailablePieceHandler availablePieceHandler=new AvailablePieceHandler((AvailablePieceRequest) request);
                    System.out.println("Available Piece Request");
                    oos.writeObject(availablePieceHandler.getResponse());
                    oos.flush();
                }else if(request.getRequestCode().equals(RequestCode.PIECEDOWNLOAD_REQUEST)){
                    String home=System.getProperty("user.home");
                    PieceDownloadRequest pieceDownloadRequest = (PieceDownloadRequest)request;
                    File file = pieceDownloadRequest.getFile();
                    String pathFolder = home+"/Downloads/" + file.getFileUID();
                    String path = pathFolder+"/"+ pieceDownloadRequest.getPieceID();
                    System.out.println("Uploading Piece : " + pieceDownloadRequest.getPieceID());
                    java.io.File piecefile = new java.io.File(path);
                    FileSender fileSender = new FileSender();

                    fileSender.sendFile(fileSender.createSocketChannel(socket.getInetAddress().getCanonicalHostName()),path);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();;
            }
        }
    }
}
