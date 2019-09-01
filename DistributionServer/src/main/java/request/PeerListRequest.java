package request;

import data.File;
import data.User;

import java.io.Serializable;

public class PeerListRequest implements Serializable
{
    private File file;
    public PeerListRequest(File file)
    {
        this.file=file;
    }
    public File getFile() throws CloneNotSupportedException
    {
        return (File) this.file.clone();
    }
}
