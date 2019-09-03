package searchHandler;

import constants.FileType;
import data.File;
import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileGetter {
    public static File getFileByUID(String UID){
        File file=null;
        String query = "Select * from File where fileUID=?";
        String tagQuery = "Select tags from FileTags where fileUID = ?";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(query);
            statement.setString(1,UID);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()) {
                file = new File(UID, resultSet.getString("fileName"), FileType.valueOf(resultSet.getString("type")), null);
            }
            statement = Main.connection.prepareStatement(tagQuery);
            statement.setString(1,UID);
            resultSet=statement.executeQuery();
            Set<String> tags=new HashSet<String>();
            while (resultSet.next()){
                tags.add(resultSet.getString(1));
            }
            file.setTags(tags);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return file;
    }
}
