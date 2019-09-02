import data.File;
import fileHandler.FileSender;
import mainApp.App;
import request.FileUploadRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws IOException {
        Set<String> s =  new HashSet<String>();

        File file =  new File("123","123","123",s);


        FileUploadRequest fileUploadRequest = new FileUploadRequest(file);
        Socket socket =  new Socket(App.serverIP,App.portNo);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(fileUploadRequest);
        oos.flush();
//        FileSender fileSender = new FileSender();
//        String path = "/home/suraj/Desktop/20174005.sql";
//        fileSender.sendFile(fileSender.createSocketChannel(),path);
    }
}
