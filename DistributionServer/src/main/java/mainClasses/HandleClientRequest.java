package mainClasses;

import authenticationHandler.Login;
import authenticationHandler.SignUp;
import constants.RequestCode;
import data.PeerList;
import request.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class
HandleClientRequest implements Runnable{
    private Socket socket ;
    ObjectOutputStream oos;
    ObjectInputStream ois ;
    public HandleClientRequest(Socket socket){
        this.socket=socket;
        try{
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
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
                    request = (Request)ois.readObject();
                }catch (EOFException e){
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

                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}
