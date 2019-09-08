package fileHandler;

import controllers.Controller_SearchFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileMerger {
    static int total = Controller_SearchFile.totalPieces;

    public static void mergeFiles(String pathofFolder,File into) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(into); BufferedOutputStream mergingStream = new BufferedOutputStream(fos)) {
            for (int i=1;i<=total;i++) {
                String filename = String.format("%05d", i);
                File f = new File(pathofFolder+"/"+filename);
                Files.copy(f.toPath(), mergingStream);
            }
            System.out.println("Merge Completed..!!");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Merging Of Files Failed");
        }
    }
}
