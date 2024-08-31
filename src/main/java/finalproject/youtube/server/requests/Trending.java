package finalproject.youtube.server.requests;

import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.TrendingRes;
import org.json.JSONObject;

public class Trending implements Request {
    private JSONObject jsonResponse;
    private TrendingRes response;

    @Override
    public void conductRequest(JSONObject request) {

    }

    @Override
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {

    }

    public TrendingRes getResponse() {
        return response;
    }
}
