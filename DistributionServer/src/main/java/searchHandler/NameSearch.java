package searchHandler;

import mainClasses.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NameSearch {

    private String searchString;

    public NameSearch(String searchString) {
        this.searchString = searchString;
    }

    public List performSearch(){
        if (searchString.isEmpty())return new ArrayList<SearchFile>();

        List files=new ArrayList<SearchFile>();

        String query="SELECT Peers.fileUID,count(*) from Peers,(Select fileUID from File where fileName like ?) as temp WHERE temp.fileUID=Peers.fileUID group by Peers.fileUID order by 2 desc";

        try {
            PreparedStatement statement= Main.connection.prepareStatement(query);
            statement.setString(1,"%"+searchString+"%");
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

    public static void main(String[] args) {
        List l=new ArrayList<String>();

        l.add("shrey");
        l.add("anuj");

        TagSearch search=new TagSearch(l);
        List files=search.performSearch();
        for (SearchFile str:
                (ArrayList<SearchFile>) files)
            System.out.println(str.getFileUID());
    }
}
