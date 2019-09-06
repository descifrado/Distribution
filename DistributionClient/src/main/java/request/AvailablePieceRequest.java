package request;

import constants.RequestCode;

import java.io.Serializable;

public class AvailablePieceRequest extends Request implements Serializable
{
    public String getFileUID() {
        return fileUID;
    }

    public void setFileUID(String fileUID) {
        this.fileUID = fileUID;
    }

    private String fileUID;

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.AVAILABLEPIECE_REQUEST;
    }
}
