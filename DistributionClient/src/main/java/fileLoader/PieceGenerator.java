package fileLoader;


import org.json.JSONException;
import org.json.JSONObject;
import tools.HashGenerator;

import java.io.*;

public class PieceGenerator {
    public static File generateJSON(File file){
        JSONObject fileData = new JSONObject();

        int pieceCounter = 1;
        int sizeOfFiles = 1024 * 32;// 32KB
        byte[] buffer = new byte[sizeOfFiles];

        String fileName = file.getName();

        //try-with-resources to ensure closing stream
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int bytesAmount = 0;
            while ((bytesAmount = bis.read(buffer)) > 0) {

                String filePartName = String.format("%05d", pieceCounter++);
//                System.out.println(filePartName);
                String hash=HashGenerator.hash(buffer);
                fileData.put(filePartName,hash);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        File jsonFile = new File(file.getParent(), fileName+".json");
        String jsonString=fileData.toString();
        jsonString=jsonString.replace(",",",\n");
        System.out.println(jsonString);
        try (FileOutputStream out = new FileOutputStream(jsonFile)) {
            out.write(jsonString.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonFile;
    }


}
