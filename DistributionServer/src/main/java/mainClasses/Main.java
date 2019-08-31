package mainClasses;

import tools.SystemProperties;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class Main {
    static String user= SystemProperties.getMySQLUserName();
    static String password=SystemProperties.getMySQLPassword();
    static String host=SystemProperties.getMySQLHostName();//"jdbc:mysql://localhost:3306/Distribution";
    static Connection connection=MysqlConnection.connect();
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;
        try{
            serverSocket = new ServerSocket(6963);
            System.out.println("Server Started..!!");
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        while(true){
            try{
                socket = serverSocket.accept();
                Thread t = new Thread(new HandleClientRequest(socket));
                t.start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

}
