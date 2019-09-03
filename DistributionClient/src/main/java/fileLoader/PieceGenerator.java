package fileLoader;


import org.json.JSONException;
import org.json.JSONObject;
import tools.HashGenerator;
import tools.UIDGenerator;

import java.io.*;
import java.util.Iterator;

public class PieceGenerator {

    public static JSONObject getJSON(File file){
        JSONObject fileData = new JSONObject();

        int pieceCounter = 1;
        int sizeOfFiles = 1024 * 32;// 32KB
        byte[] buffer = new byte[sizeOfFiles];

        String fileName = file.getName();

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
        return fileData;
    }

    public static String generateHash(JSONObject json){

        String hash="";
        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            try {
                String thash=json.getString(key);
                hash=HashGenerator.hash(hash+thash);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return UIDGenerator.generateuid(hash);
    }

    public static String generateJSON(JSONObject jsonObject,File file){


        String fileName = file.getName();

        File jsonFile = new File(file.getParent(), fileName+".json");
        String jsonString=jsonObject.toString();
        jsonString=jsonString.replace(",",",\n");
        System.out.println(jsonString);
        try (FileOutputStream out = new FileOutputStream(jsonFile)) {
            out.write(jsonString.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return generateHash(jsonObject);
    }

}
