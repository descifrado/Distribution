package fileHandler;

import constants.ResponseCode;
import data.File;
import mainClasses.Main;
import request.DownloadedFileRequest;
import request.Response;
import searchHandler.FileGetter;
import tools.UIDGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DownloadedFile
{
    private DownloadedFileRequest downloadedFileRequest;
    private String userUID;
    public DownloadedFile(DownloadedFileRequest downloadedFileRequest)
    {
        this.downloadedFileRequest=downloadedFileRequest;
        this.userUID=downloadedFileRequest.getUserUID();
    }
    public Response getResponse()
    {
        userUID=downloadedFileRequest.getUserUID();
        String query="SELECT distinct fileUID from UserHistory WHERE userUID=\""+userUID+"\" and downloaded=\"1\";";
        try
        {
            PreparedStatement preparedStatement= Main.connection.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();
            List<File> files=new ArrayList<File>();
            while (rs.next())
            {
                files.add(FileGetter.getFileByUID(rs.getString("fileUID")));
            }
            return new Response(UIDGenerator.generateuid(), files, ResponseCode.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Response(UIDGenerator.generateuid(), null, ResponseCode.FAILED);
        }
    }
}
