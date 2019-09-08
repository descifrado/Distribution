package fileHandler;

import constants.ResponseCode;
import mainClasses.Main;
import request.Response;
import request.UpdateUserHistoryRequest;
import tools.UIDGenerator;

import java.sql.PreparedStatement;

public class UpdateUserHistory
{
    private UpdateUserHistoryRequest updateUserHistoryRequest;
    private String userUID,fileUID,downloded,shared;
    public UpdateUserHistory(UpdateUserHistoryRequest updateUserHistoryRequest)
    {
        this.updateUserHistoryRequest=updateUserHistoryRequest;
    }
    public Response getResponse()
    {
        this.userUID=updateUserHistoryRequest.getUserUID();
        this.fileUID=updateUserHistoryRequest.getFileUID();
        this.downloded=updateUserHistoryRequest.getDownloaded();
        this.shared=updateUserHistoryRequest.getShared();
        String query="INSERT INTO UserHistory VALUES(?,?,?,?);";
        try
        {
            PreparedStatement preparedStatement= Main.connection.prepareStatement(query);
            preparedStatement.setString(1,userUID);
            preparedStatement.setString(2,fileUID);
            preparedStatement.setString(3,downloded);
            preparedStatement.setString(4,shared);
            preparedStatement.executeUpdate();
            return new Response(UIDGenerator.generateuid(), null, ResponseCode.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Response(UIDGenerator.generateuid(),null,ResponseCode.FAILED);
        }
    }
}
