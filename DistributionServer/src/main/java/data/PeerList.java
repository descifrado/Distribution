package data;

import constants.ResponseCode;
import mainClasses.Main;
import request.PeerListRequest;
import request.Response;
import tools.UIDGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PeerList
{
    private String fileUID;
    public class Peer
    {
        private String userUID,ip;

        public String getUserUID() {
            return userUID;
        }

        public String getIp() {
            return ip;
        }

        public Peer(String userUID, String ip)
        {
            this.userUID=userUID;
            this.ip=ip;
        }

    }
    List<Peer> peerList;

    public Response getPeerList() {
        Response response;
        String query="Search userID, IP from Peers where fileUID=?;";
        try
        {
            PreparedStatement stmt= Main.connection.prepareStatement(query);
            stmt.setString(1,this.fileUID);
            ResultSet rs= stmt.executeQuery();
            while(rs.next())
            {
                String userid=rs.getString("userUID");
                String iP=rs.getString("IP");
                peerList.add(new Peer(userid, iP));
            }
            response = new Response(UIDGenerator.generateuid(),peerList,ResponseCode.SUCCESS);
            return response;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return new Response(UIDGenerator.generateuid(),null, ResponseCode.FAILED);
    }

    public PeerList(PeerListRequest request) throws CloneNotSupportedException
    {
        this.fileUID=request.getFile().getFileUID();
    }
}
