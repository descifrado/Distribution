package fileHandler;

import constants.ResponseCode;
import mainClasses.Main;
import request.FileUploadRequest;
import request.Response;
import tools.UIDGenerator;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class FileUploadHandler {
    FileUploadRequest fileUploadRequest;
    String fileLocation;
    public FileUploadHandler(FileUploadRequest fileUploadRequest,String fileLocation){
        this.fileUploadRequest = fileUploadRequest;
        this.fileLocation = fileLocation;
    }

    public Response getResponse(){

        if(!new File(fileLocation).exists()){
            return new Response(UIDGenerator.generateuid(),null,ResponseCode.FAILED);
        }
        data.File file = fileUploadRequest.getFile();
        String q1="INSERT INTO File VALUES (?,?,?,?);";
        String q2="INSERT INTO FileTags VALUES (?,?);";
        try{
            PreparedStatement preparedStatement = Main.connection.prepareStatement(q1);
            preparedStatement.setString(1,file.getFileUID());
            preparedStatement.setString(2,file.getFileName());
            preparedStatement.setString(3,fileLocation);
            preparedStatement.setString(4,file.getType());
            preparedStatement.executeUpdate();

            preparedStatement = Main.connection.prepareStatement(q2);
            Set<String> tags = file.getTags();
            for(String tag : tags){
                preparedStatement.setString(1,file.getFileUID());
                preparedStatement.setString(2,tag);
                preparedStatement.executeUpdate();
            }
            return new Response(UIDGenerator.generateuid(),null,ResponseCode.SUCCESS);

        }catch (SQLException e){
            File f=new File(fileLocation+"/"+file.getFileUID());
            f.delete();
            e.printStackTrace();
        }

        return new Response(UIDGenerator.generateuid(),null,ResponseCode.FAILED);
    }
}
