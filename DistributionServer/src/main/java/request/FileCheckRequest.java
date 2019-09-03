package request;

import constants.RequestCode;
import data.File;

public class FileCheckRequest extends  Request {
    private File file;
    public FileCheckRequest(File file){
        this.file = file;
    }

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.FILECHECK_REQUEST;
    }

    public File getFile() {
        return file;
    }
}
