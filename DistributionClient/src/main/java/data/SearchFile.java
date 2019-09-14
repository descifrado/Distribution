package data;
/**
 *  Serializable class for Object Sharing over Socket
 */

import java.io.Serializable;

public class SearchFile extends File implements Serializable,Comparable {
    private int peers;

    /**
     *
     * @param file object containing details of a file
     * @param peers number of peers with the file
     */
    public SearchFile(File file, int peers) {
        super(file.getFileUID(),file.getFileName(),file.getType(),file.getTags());
        this.peers = peers;
    }


    @Override
    public String toString() {
        return super.getFileName();
    }

    @Override
    public int compareTo(Object o) {
        SearchFile temp= (SearchFile) o;
        return Integer.valueOf(temp.peers).compareTo(this.peers);
    }
}
