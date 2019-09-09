package mainClasses;

import authenticationHandler.Login;
import authenticationHandler.SignUp;
import constants.RequestCode;
import fileHandler.*;
import peerListHandler.PeerList;
import request.*;
import searchHandler.Search;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class
HandleClientRequest implements Runnable{
    private Socket socket ;
    ObjectOutputStream oos;
    ObjectInputStream ois ;
    String userIP;
    public HandleClientRequest(Socket socket){
        this.socket=socket;
        System.out.println(socket.getInetAddress().getCanonicalHostName());
        try{
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            userIP = socket.getInetAddress().getCanonicalHostName();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        Request request = null;
        while(true){
            try{

                try{
                    Object object=ois.readObject();
                    System.out.println(object.getClass());
                    request = (Request)object;
                }catch (EOFException e){
                    System.out.println("Client Disconnected..!!");
                    return;
                }catch (SocketException e){
                    System.out.println("Client Disconnected..!!");
                    return;
                }

                if(request.getRequestCode().equals(RequestCode.SIGNUP_REQUEST)) {
                    SignUp signUp = new SignUp((SignUpRequest) request);
                    Response response = signUp.insert();
                    oos.writeObject(response);
                    oos.flush();
                }else if(request.getRequestCode().equals(RequestCode.LOGIN_REQUEST)){
                    Login login = new Login((LoginRequest)request);
                    oos.writeObject(login.getResponse());
                    oos.flush();
                }else if(request.getRequestCode().equals(RequestCode.PEERLIST_REQUEST)){
                    PeerList peerList = new PeerList((PeerListRequest) request);
                    oos.writeObject(peerList.getPeerList());
                    oos.flush();
                }else if(request.getRequestCode().equals(RequestCode.FILEUPLOAD_REQUEST)){
                    FileReciever fileReciever = new FileReciever();
                    String cwd=System.getProperty("user.dir");
                    fileReciever.readFile(fileReciever.createSocketChannel(Main.getServerSocketChannel()),((FileUploadRequest)request).getFile().getFileUID(),cwd+"/jsonFiles");
                    FileUploadHandler fileUploadHandler = new FileUploadHandler((FileUploadRequest)request,cwd+"/jsonFiles",this.userIP);
                    oos.writeObject(fileUploadHandler.getResponse());
                    oos.flush();
                }else  if(request.getRequestCode().equals(RequestCode.FILECHECK_REQUEST)){
                    FileCheckHandler fileCheckHandler = new FileCheckHandler((FileCheckRequest)request,this.userIP);
                    oos.writeObject(fileCheckHandler.getResponse());
                    oos.flush();
                }
                else if (request.getRequestCode().equals(RequestCode.SEARCH_REQUEST)){
                    SearchRequest searchRequest= (SearchRequest) request;
                    Search search=new Search((SearchRequest) request);
                    oos.writeObject(search.performSearch());
                    oos.flush();
                }else if(request.getRequestCode().equals(RequestCode.FILEDOWNLOAD_REQUEST)){
                    String cwd = System.getProperty("user.dir");
                    String loc = cwd + "/jsonFiles/" ;
                    FileDownloadRequest fileDownloadRequest = (FileDownloadRequest)request;
                    loc+=fileDownloadRequest.getFile().getFileUID();
                    FileSender fileSender = new FileSender();
                    fileSender.sendFile(fileSender.createSocketChannel(socket.getInetAddress().getCanonicalHostName()),loc);

                }
                else if(request.getRequestCode().equals(RequestCode.DOWNLOADEDFILES_REQUEST))
                {
                    DownloadedFile downloadedFile=new DownloadedFile((DownloadedFileRequest) request);
                    oos.writeObject(downloadedFile.getResponse());
                    oos.flush();
                }
                else if(request.getRequestCode().equals(RequestCode.SHAREDFILES_REQUEST))
                {
                    SharedFile sharedFile=new SharedFile((SharedFileRequest)request);
                    oos.writeObject(sharedFile.getResponse());
                    oos.flush();
                }
                else if(request.getRequestCode().equals(RequestCode.UPDATEUSERHISTORY_REQUEST)) {
                    UpdateUserHistory updateUserHistory = new UpdateUserHistory((UpdateUserHistoryRequest) request);
                    oos.writeObject(updateUserHistory.getResponse());
                    oos.flush();
                }else if(request.getRequestCode().equals(RequestCode.FILEDOWNLOADCOMPLETE_REQUEST)){
                    FileDownloadCompleteRequest fileDownloadCompleteRequest = (FileDownloadCompleteRequest)request;
                    String cwd=System.getProperty("user.dir");
                    FileDownloadCompleteHandler filedch = new FileDownloadCompleteHandler(fileDownloadCompleteRequest,cwd+"/jsonFiles",this.userIP);
                    oos.writeObject(filedch.getResponse());
                    oos.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}
