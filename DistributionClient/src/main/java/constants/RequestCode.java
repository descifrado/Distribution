package constants;

public enum RequestCode {

    LOGIN_REQUEST,
    SIGNUP_REQUEST,
    PEERLIST_REQUEST,
    SEARCH_REQUEST,
    FILEUPLOAD_REQUEST,
    FILECHECK_REQUEST,
    AVAILABLEPIECE_REQUEST,
    FILEDOWNLOAD_REQUEST,
    PIECEDOWNLOAD_REQUEST;

    RequestCode(){
        this.toString();
    }
}
