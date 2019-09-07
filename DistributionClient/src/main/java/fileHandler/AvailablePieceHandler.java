package fileHandler;

import constants.ResponseCode;
import netscape.javascript.JSObject;
import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.json.JSONTokener;
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

        JSONTokener jsonParser;

        try(FileReader reader=new FileReader(path))
        {
            jsonParser=new JSONTokener(reader);
            jsonObject=new JSONObject(jsonParser);
            return new Response(UIDGenerator.generateuid(), jsonObject.toString(), ResponseCode.SUCCESS);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Response(UIDGenerator.generateuid(),null,ResponseCode.FAILED);
        }
    }
}
