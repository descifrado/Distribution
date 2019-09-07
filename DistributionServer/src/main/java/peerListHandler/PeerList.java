package peerListHandler;

import constants.ResponseCode;
import data.Peer;
import mainClasses.Main;
import request.PeerListRequest;
import request.Response;
import tools.UIDGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeerList
{
    private String fileUID;


    public Response getPeerList() {
        Response response;
        List<Peer> peerList;
        peerList=new ArrayList<Peer>();
        String query="SELECT userUID, IP from Peers where fileUID=?;";
        try
        {
            PreparedStatement stmt= Main.connection.prepareStatement(query);
            stmt.setString(1,this.fileUID);
            System.out.println(stmt);
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

    public PeerList(PeerListRequest request)
    {
        try {
            this.fileUID=request.getFile().getFileUID();
        } catch (CloneNotSupportedException e) {
            this.fileUID="";
            e.printStackTrace();
        }
    }
}
