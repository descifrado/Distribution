package mainApp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

public class Handler implements Runnable {
    public static ServerSocketChannel getServerSocketChannel(){
        return serverSocketChannel;
    }
    private static ServerSocketChannel serverSocketChannel = null;

    @Override
    public void run() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerSocket serverSocket;
        Socket socket;
        try{
            serverSocket = new ServerSocket(6963);
            System.out.println("Client Started..!!");
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
