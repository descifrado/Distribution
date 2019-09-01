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
        List l=new ArrayList<Integer>();
        l.add(5);
        l.add(6);
        l.add(10);
        l.add(15);

        List m=new ArrayList<Integer>();
        m.add(4);
        m.add(6);
        m.add(15);
        m.add(20);

        l.retainAll(m);
        System.out.println(l);

   //     l.add("shrey");
   //     l.add("anuj");

        NameSearch search=new NameSearch("s");
        List files=search.performSearch();
        for (Object str:
             files) {
            System.out.println(str);

        }
    }
}
