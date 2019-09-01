package searchHandler;

import data.File;
import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileGetter {
    public static File getFileByUID(String UID){
        File file;
        String query = "Select * from File where fileUID=?";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(query);
            statement.setString(1,UID);
            ResultSet resultSet=statement.executeQuery();
        //    file=new File(UID,resultSet.getString("fileName"),resultSet.getString("location"),resultSet.getType("type"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
