package fileHandler;

import constants.ResponseCode;
import mainClasses.Main;
import request.FileCheckRequest;
import request.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileCheckHandler {
    private FileCheckRequest fileCheckRequest;
    private String fileUID;
    public FileCheckHandler(FileCheckRequest fileCheckRequest){
        this.fileCheckRequest = fileCheckRequest;
        this.fileUID = fileCheckRequest.getFile().getFileUID();
    }

    public Response getResponse(){
        String query = "SELECT * FROM File WHERE fileUID = \"" + this.fileUID + "\";";
        try{
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()){
//                Need to Enter new Tags in Tags Table before sending response.
                return new Response(null,null, ResponseCode.SUCCESS);
            }
            return new Response(null,null,ResponseCode.FAILED);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
