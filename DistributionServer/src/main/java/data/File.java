package data;


import java.util.Set;

/**
 *
 */
public class File {
    /**
     *
     **/
    private String fileUID;
    private String fileName;
    private String  type;
    private Set tags;

    /**
     *
     * @param fileUID
     * @param fileName
     * @param type
     * @param tags
     */
    public File(String fileUID, String fileName, String type, Set tags) {
        this.fileUID = fileUID;
        this.fileName = fileName;
        this.type = type;
        this.tags = tags;
    }

    /**
     *
     * @return
     */
    public String getFileUID() {
        return fileUID;
    }

    /**
     *
     * @param fileUID
     */
    public void setFileUID(String fileUID) {
        this.fileUID = fileUID;
    }

    /**
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**.
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
    public Set getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     */
    public void setTags(Set tags) {
        this.tags = tags;
    }
}
