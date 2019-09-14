package constants;

/**
 *  Enum for defining constants for the file type
 */
public enum FileType {

    MEDIA,
    ARCHIVE,
    TEXT,
    IMAGE,
    AUDIO,
    OTHER,
    DOCUMENT,
    ALL
    ;

    /**
     * @param not required
     *
     */
    FileType(){
        this.toString();
    }
}
