package mainApp;

import com.sun.tools.javac.Main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

public class Handler implements Runnable {


    @Override
    public void run() {
        try {

            while (true) {
                try {

                    Socket socketp2p = App.serverSocket.accept();
                    Thread t = new Thread(new HandleClientRequest(socketp2p));
                    t.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
