package request;

public class Response {
    String responseID;
    Object responseObject;
    boolean responseCode;

    public Response(String responseID, Object responseObject, boolean responseCode) {
        this.responseID = responseID;
        this.responseObject = responseObject;
        this.responseCode = responseCode;
    }

    public String getResponseID() {
        return responseID;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public boolean getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(boolean responseCode) {
        this.responseCode = responseCode;
    }
}
