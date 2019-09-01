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
                request = (Request)ois.readObject();
            }
            catch (IOException e){
                e.printStackTrace();
                return;
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }


            if(request.getRequestCode().equals(RequestCode.SIGNUP_REQUEST)) {

                try {
                    SignUp signUp = new SignUp((SignUpRequest) request);
                    Response response = signUp.insert();
                    oos.writeObject(response);
                    oos.flush();
                }catch (CloneNotSupportedException e){
                    e.printStackTrace();
                } catch (SocketException e){
                    System.out.println("Client Disconneccted !!");
                    return;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }else if(request.getRequestCode().equals(RequestCode.LOGIN_REQUEST)){
                Login login = new Login((LoginRequest)request);
                try{
                    oos.writeObject(login.getResponse());
                    oos.flush();

                }
                catch (EOFException e){
                    System.out.println("Client Disconnected !!!");
                    return;
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }else if(request.getRequestCode().equals(RequestCode.PEERLIST_REQUEST)){
                try {
                    PeerList peerList = new PeerList((PeerListRequest) request);
                    oos.writeObject(peerList.getPeerList());
                    oos.flush();
                }catch (CloneNotSupportedException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }
}
