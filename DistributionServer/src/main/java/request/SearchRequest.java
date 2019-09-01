package request;

import constants.RequestCode;
import data.File;

import java.util.List;

public class SearchRequest extends Request{

    private String name;
    private String type;
    private List<String> tags;

    public SearchRequest(String name, String type, List<String> tags) {
        this.name = name;
        this.type = type;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public RequestCode getRequestCode(){

        return null;
    }
}
