package searchHandler;

import constants.FileType;
import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TypeSearch {

    private FileType fileType;

    public TypeSearch(FileType fileType) {
        this.fileType = fileType;
    }

    public List performSearch() {
        if (fileType.equals(FileType.ALL))return new ArrayList<SearchFile>();

        List files = new ArrayList<SearchFile>();

        String query="SELECT Peers.fileUID,count(*) from Peers,(Select fileUID from File where type = ?) as temp WHERE temp.fileUID=Peers.fileUID group by Peers.fileUID order by 2 desc";

        try {
            PreparedStatement statement= Main.connection.prepareStatement(query);
            statement.setString(1,fileType.toString());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                SearchFile file=new SearchFile(resultSet.getString(1),resultSet.getInt(2));
                files.add(file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }
}
