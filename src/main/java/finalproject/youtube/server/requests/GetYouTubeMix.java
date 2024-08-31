package finalproject.youtube.server.requests;

import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.YouTubeMixRes;
import org.json.JSONObject;

public class GetYouTubeMix implements Request {
    private JSONObject jsonResponse;
    private int SQLResponse;
    private YouTubeMixRes response;

    @Override
    public void conductRequest(JSONObject request) {

    }

    @Override
    public JSONObject getJsonResponse() {
        return null;
    }

    @Override
    public void makeJson(int res) {

    }

    public YouTubeMixRes getResponse() {
        return response;
    }
}
