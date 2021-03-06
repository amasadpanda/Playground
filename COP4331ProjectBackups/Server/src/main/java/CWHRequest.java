import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class CWHRequest {

    public enum RequestType {
        MAKE_MOVE, LOGIN, ACCEPT_INVITE, FRIEND_REQUEST, GAME_CREATION, MATCHMAKING_REQUEST
    }

    @Expose
    private String authID;

    @Expose
    private RequestType requestType;

    @Expose
    private HashMap<String, String> extras;

    public CWHRequest(String authID, RequestType requestType)
    {
        this.authID = authID;
        this.requestType = requestType;
        this.extras = new HashMap<String, String>();
    }

    public String getAuthID() {
        return authID;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public HashMap<String, String> getExtras() {
        return extras;
    }

    private String getJSON()
    {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this);
    }
}
