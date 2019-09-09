package fileHandler;

import constants.ResponseCode;
import data.File;
import mainClasses.Main;
import request.Response;
import request.SharedFileRequest;
import searchHandler.FileGetter;
import tools.SharedFileResponse;
import tools.UIDGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SharedFile
{
    private SharedFileRequest sharedFileRequest;
    private String userUID;
    List<File> files;
    List<Integer> noOfDownloads;
    public SharedFile(SharedFileRequest sharedFileRequest)
    {
        this.sharedFileRequest=sharedFileRequest;
        this.userUID=sharedFileRequest.getUserUID();
        noOfDownloads=new ArrayList<Integer>();
    }
    public Response getResponse()
    {
        userUID=sharedFileRequest.getUserUID();
        String query="SELECT fileUID from UserHistory WHERE userUID=\""+userUID+"\" and shared=\"1\";";
        try
        {
            PreparedStatement preparedStatement= Main.connection.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();
            files=new ArrayList<File>();
            while (rs.next())
            {
                files.add(FileGetter.getFileByUID(rs.getString("fileUID")));
            }
            for(File file: files)
            {
                String fileUID=file.getFileUID();
                String query1="Select count(*) from UserHistory where fileUID=? and downloaded=?;";
                try
                {
                    PreparedStatement preparedStatement1= Main.connection.prepareStatement(query1);
                    preparedStatement1.setString(1,fileUID);
                    preparedStatement1.setString(2,"1");
                    ResultSet rs1=preparedStatement1.executeQuery();
                    rs1.next();
                    noOfDownloads.add(rs1.getInt(1));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            SharedFileResponse sharedFileResponse=new SharedFileResponse(files,noOfDownloads);
            return new Response(UIDGenerator.generateuid(), sharedFileResponse, ResponseCode.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Response(UIDGenerator.generateuid(), null, ResponseCode.FAILED);
        }
    }
}
