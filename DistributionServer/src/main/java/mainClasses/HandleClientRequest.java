package mainClasses;

import authenticationHandler.Login;
import authenticationHandler.SignUp;
import constants.RequestCode;
import peerListHandler.PeerList;
import fileHandler.FileCheckHandler;
import fileHandler.FileReciever;
import fileHandler.FileUploadHandler;
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
                    System.out.print(searchRequest.getTags()+" ");
                    System.out.print(searchRequest.getName()+" ");
                    System.out.println(searchRequest.getType());
                    Search search=new Search((SearchRequest) request);
                    oos.writeObject(search.performSearch());
                    oos.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}
