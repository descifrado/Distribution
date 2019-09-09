package tools;

import data.File;
import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SharedFileResponse
{
    private List<File> files;
    private List<Integer> noOfDownloads;
    public SharedFileResponse(List<File> files, List<Integer> noOfDownloads)
    {
        this.files=files;
        this.noOfDownloads=noOfDownloads;
    }
    public void setValues()
    {
        for(File file: files)
        {
            String fileUID=file.getFileUID();
            String query="Select count(*) from UserHistroy where fileUID=? and downloaded=?;";
            try
            {
                PreparedStatement preparedStatement= Main.connection.prepareStatement(query);
                preparedStatement.setString(1,fileUID);
                preparedStatement.setString(2,"1");
                ResultSet rs=preparedStatement.executeQuery();
                rs.next();
                noOfDownloads.add(rs.getInt(1));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
