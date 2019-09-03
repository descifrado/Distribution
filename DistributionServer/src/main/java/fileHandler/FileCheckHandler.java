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
    private String userUID;
    private String userIP;
    public FileCheckHandler(FileCheckRequest fileCheckRequest){
        this.fileCheckRequest = fileCheckRequest;
        this.fileUID = fileCheckRequest.getFile().getFileUID();
        this.userUID = fileCheckRequest.getUserUID();
        this.userIP = fileCheckRequest.getUserIP();
    }

    public Response getResponse(){
        String query = "SELECT * FROM File WHERE fileUID = \"" + this.fileUID + "\";";
        try{
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) {

                return new Response(null, null, ResponseCode.SUCCESS);
            }
            String q="INSERT INTO Peers VALUES (?,?,?);";
            PreparedStatement stmt= Main.connection.prepareStatement(q);
            stmt.setString(1,this.fileUID);
            stmt.setString(2,this.userUID);
            stmt.setString(3,this.userIP);
            stmt.executeUpdate();

//                Need to Enter new Tags in Tags Table before sending response.
            return new Response(null,null,ResponseCode.FAILED);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
