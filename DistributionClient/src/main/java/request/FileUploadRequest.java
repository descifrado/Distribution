package request;

import constants.RequestCode;
import data.File;

import java.io.Serializable;

public class FileUploadRequest extends Request implements Serializable {
    File file;
    private static final long serialVersionUID = 1L;
    public FileUploadRequest(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.FILEUPLOAD_REQUEST;
    }

}
