package fileHandler;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class FileSplit implements Runnable {

    private File f;
    private int sizeOfPart;
    private String location;
    public FileSplit(File f,int sizeOfPart,String location){
        this.f = f;
        this.sizeOfPart = sizeOfPart;
        this.location = location;
    }


    @Override
    public void run() {
        int partCounter = 1;

//        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfPart];

        String fileName = f.getName();

        try (FileInputStream fis = new FileInputStream(f);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int bytesAmount = 0;
            while ((bytesAmount = bis.read(buffer)) > 0) {

                String filePartName = String.format("%05d", partCounter++);
                File newFile = new File(location, filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}