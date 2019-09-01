package searchHandler;

import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagSearch {

    private List searchTags;

    public TagSearch(List searchTags) {
        this.searchTags = searchTags;
    }

    public Set performSearch(){
        if (searchTags.isEmpty())return new HashSet();

        Set files=new HashSet<String>();

        String query="Select distinct fileUID from FileTags where tags=? ";
        int num=searchTags.size();
        for (int i = 1; i < num; i++) {
            query+="or tags=? ";
        }
        try {
            PreparedStatement statement= Main.connection.prepareStatement(query);
            for (int i = 1; i <= num; i++) {
                statement.setString(i, (String) searchTags.get(i-1));
            }
            System.out.println(statement);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                files.add(resultSet.getString("fileUID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  files;


    }
}
