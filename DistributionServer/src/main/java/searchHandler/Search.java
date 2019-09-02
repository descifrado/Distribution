package searchHandler;

import constants.ResponseCode;
import data.File;
import request.Response;
import request.SearchRequest;
import tools.UIDGenerator;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private SearchRequest searchRequest;

    public Search(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public Response performSearch() {

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

        List searchResult=new ArrayList<data.SearchFile>();
        for (Object i: searchedList
             ) {
            SearchFile temp = (SearchFile) i;
            File file=FileGetter.getFileByUID(temp.getFileUID());
            searchResult.add(new data.SearchFile(file,temp.getPeers()));
        }
        Response response=new Response(UIDGenerator.generateuid(),searchResult, ResponseCode.SUCCESS);
        return response;
    }


}
