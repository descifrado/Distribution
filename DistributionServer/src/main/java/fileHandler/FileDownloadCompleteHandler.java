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

public class FileDownloadCompleteHandler {
    FileUploadRequest fileUploadRequest;
    String fileLocation;
    private String userUID;
    private String userIP;
    data.File file ;
    public FileDownloadCompleteHandler(FileUploadRequest fileUploadRequest,String fileLocation,String userIP){
        this.fileUploadRequest = fileUploadRequest;
        this.fileLocation = fileLocation;
        this.userUID = fileUploadRequest.getUserUID();
        this.userIP = userIP;
        this.file = fileUploadRequest.getFile();
    }

    public Response getResponse(){

        if(!new File(fileLocation).exists()){
            return new Response(UIDGenerator.generateuid(),null, ResponseCode.FAILED);
        }


        try{

            String q="INSERT INTO Peers VALUES (?,?,?);";
            PreparedStatement stmt= Main.connection.prepareStatement(q);
            stmt.setString(1,this.fileUploadRequest.getFile().getFileUID());
            stmt.setString(2,this.userUID);
            stmt.setString(3,this.userIP);
            stmt.executeUpdate();

            //Inserting in User History
            String query="INSERT INTO UserHistory VALUES(?,?,?,?);";
            PreparedStatement statement=Main.connection.prepareStatement(query);
            statement.setString(1,userUID);
            statement.setString(2,file.getFileUID());
            statement.setString(3,"1");
            statement.setString(4,"0");
            statement.executeUpdate();

            return new Response(UIDGenerator.generateuid(),null,ResponseCode.SUCCESS);

        }catch (SQLException e){

            e.printStackTrace();
        }

        return new Response(UIDGenerator.generateuid(),null,ResponseCode.FAILED);
    }
}
