package request;

import constants.RequestCode;
import data.File;

public class FileUploadRequest extends Request{
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
