package fileHandler;

import request.AvailablePieceRequest;
import request.Request;
import request.Response;

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
        return null;
    }
}
