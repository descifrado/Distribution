package fileHandler;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class FileReciever {


    public SocketChannel createSocketChannel(){
        try {
            ServerSocketChannel serverSocketChannel = null;
            SocketChannel socketChannel = null;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9000));
            socketChannel = serverSocketChannel.accept();
            System.out.println("Connected With Client For File IO");
            return socketChannel;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void readFile(SocketChannel socketChannel,String fileName){
        RandomAccessFile aFile = null;
        try {
            String cwd = System.getProperty("user.dir");
            File file = new File(cwd + "/" + fileName);
            file.createNewFile();
            aFile = new RandomAccessFile(cwd+"/"+fileName,"rw");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileChannel fileChannel = aFile.getChannel();
            while (socketChannel.read(buffer)> 0) {
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
            }
            Thread.sleep(1000);
            fileChannel.close();
            System.out.println("End of file reached..Closing channel");
            socketChannel.close();
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
