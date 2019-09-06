package fileHandler;

import constants.ResponseCode;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import request.AvailablePieceRequest;
import request.Request;
import request.Response;
import tools.UIDGenerator;

import java.io.FileReader;

public class AvailablePieceHandler
{
    private AvailablePieceRequest availablePieceRequest;
    private String fileUID;
    public AvailablePieceHandler(AvailablePieceRequest availablePieceRequest)
    {
        this.availablePieceRequest=availablePieceRequest;
        this.fileUID=availablePieceRequest.getFileUID();
    }
    public Response getResponse()

    {
        String home=System.getProperty("user.home");
        String path=fileUID+"downloaded.json";
        path=home+"/Downloads/"+path;
        JSONObject jsonObject;
        JSONParser jsonParser=new JSONParser();
        try(FileReader reader=new FileReader(path))
        {
            jsonObject=(JSONObject) jsonParser.parse(reader);
            return new Response(UIDGenerator.generateuid(), jsonObject, ResponseCode.SUCCESS);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Response(UIDGenerator.generateuid(),null,ResponseCode.FAILED);
        }
    }
}
