package request;

import constants.RequestCode;

import java.util.List;

/**
 *  Request class to handle search utility
 */
public class SearchRequest extends Request{

    /**
     *  Variable for search string
     */
    private String name;
    private String type;
    private List<String> tags;

    /**
     * Constructor for initializing search request
     * @param name Search String for search by name
     * @param type Search String for search by type
     * @param tags List of String for search by tags
     */
    public SearchRequest(String name, String type, List<String> tags) {
        this.name = name;
        this.type = type;
        this.tags = tags;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     */
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.SEARCH_REQUEST;
    }
}
