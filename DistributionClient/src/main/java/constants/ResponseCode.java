package constants;

/**
 *  constants for recieving the status of response for any request and work accordingly
 */
public enum ResponseCode {
    SUCCESS,
    FAILED;

    ResponseCode(){
        this.toString();
    }
}
