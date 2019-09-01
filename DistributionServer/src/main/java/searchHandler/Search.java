package searchHandler;

import request.SearchRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Search {
    private SearchRequest searchRequest;

    public Search(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public List performSearch() {

        List filesByName = new NameSearch(searchRequest.getName()).performSearch();
        List filesByType = new TypeSearch(searchRequest.getType()).performSearch();
        List filesByTag = new TagSearch(searchRequest.getTags()).performSearch();

        List searchedList = new ArrayList<SearchFile>();
        if(!filesByName.isEmpty()){
            searchedList.addAll(filesByName);
        }
        if (searchedList.isEmpty() && !filesByType.isEmpty()){
            searchedList.addAll(filesByType);
        }
        else if (!searchedList.isEmpty() && !filesByType.isEmpty()){
            searchedList.retainAll(filesByType);
        }
        if (searchedList.isEmpty() && !filesByTag.isEmpty()){
            searchedList.addAll(filesByTag);
        }
        else if (!searchedList.isEmpty() && !filesByTag.isEmpty()){
            searchedList.retainAll(filesByTag);
        }


        return null;
    }


}
