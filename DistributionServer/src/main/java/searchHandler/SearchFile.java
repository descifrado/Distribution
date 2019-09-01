package searchHandler;

public class SearchFile implements Comparable{
    private String fileUID;
    private Integer peers;

    public SearchFile(String fileUID, Integer peers) {
        this.fileUID = fileUID;
        this.peers = peers;
    }

    public String getFileUID() {
        return fileUID;
    }

    public void setFileUID(String fileUID) {
        this.fileUID = fileUID;
    }

    public Integer getPeers() {
        return peers;
    }

    public void setPeers(Integer peers) {
        this.peers = peers;
    }

    @Override
    public int compareTo(Object o) {
        SearchFile temp= (SearchFile) o;
        return Integer.valueOf(temp.peers).compareTo(this.peers);
    }
}
