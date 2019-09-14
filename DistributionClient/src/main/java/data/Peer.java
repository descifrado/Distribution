package data;
/**
 *  Serializable class for Object Sharing over Socket with details of peers
 */

import java.io.Serializable;

public class Peer implements Serializable {
    private String userUID,ip;

    /**
     *
     * @return User UID of the Peer
     */
    public String getUserUID() {
        return userUID;
    }

    /**
     *
     * @return IP of the peer when connected to the network
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @param userUID Unique ID of each Peer
     * @param ip of peer
     */
    public Peer(String userUID, String ip)
    {
        this.userUID=userUID;
        this.ip=ip;
    }
}
