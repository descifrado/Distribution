package searchHandler;

import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagSearch {

    private List searchTags;

    public TagSearch(List searchTags) {
        this.searchTags = searchTags;
    }

    public List performSearch(){
        if (searchTags.isEmpty())return new ArrayList<SearchFile>();

        List files=new ArrayList<SearchFile>();

        String tempQuery="Select distinct fileUID from FileTags where tags=? ";
        int num=searchTags.size();
        for (int i = 1; i < num; i++) tempQuery += "or tags=? ";
        String query="SELECT Peers.fileUID,count(*) from Peers,("+tempQuery+") as temp WHERE temp.fileUID=Peers.fileUID group by Peers.fileUID order by 2 desc";
        try {
            PreparedStatement statement= Main.connection.prepareStatement(query);
            for (int i = 1; i <= num; i++) {
                statement.setString(i, (String) searchTags.get(i-1));
            }
            System.out.println(statement);
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
