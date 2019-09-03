package request;

import constants.RequestCode;
import data.File;

import java.io.Serializable;

public class FileUploadRequest extends Request implements Serializable {
//    private static final long serialVersionUID=1L;
    File file;

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
