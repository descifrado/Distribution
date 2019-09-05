package fileHandler;

import constants.ResponseCode;
import data.File;
import mainClasses.Main;
import request.FileDownloadRequest;
import request.Response;
import tools.UIDGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDownloadHandler {
    private File file ;
    public FileDownloadHandler(FileDownloadRequest fileDownloadRequest){
        this.file = fileDownloadRequest.getFile();
    }
    public Response getResponse(){
        List<String> ipList = new ArrayList<String>();
        String fileUID = this.file.getFileUID();
        String query = "SELECT IP FROM Peers WHERE fileUID = \"" +fileUID + "\";";
        try{
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                ipList.add(rs.getString(1));
            }
            return new Response(UIDGenerator.generateuid(),ipList, ResponseCode.SUCCESS);
        }catch (SQLException e){

            e.printStackTrace();
            return new Response(UIDGenerator.generateuid(),ipList, ResponseCode.FAILED);
        }

    }

}
