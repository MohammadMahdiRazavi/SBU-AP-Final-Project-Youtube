package finalproject.youtube.server.requests;

import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.RecommendedVideosRes;
import org.json.JSONObject;

public class GetRecommendedVideos implements Request {
    private JSONObject jsonResponse;
    private RecommendedVideosRes response;

    @Override
    public void conductRequest(JSONObject request) {
        //TODO -> call the database here
        response = null;
        int SQLResponse = response.getSQLResponse();
        makeJson(SQLResponse);
    }

    @Override
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {
        jsonResponse = new JSONObject();
        if (res == 1){
            jsonResponse.put("response", "Successfully got recommended videos");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get recommended videos");
        }
    }

    public RecommendedVideosRes getResponse() {
        return response;
    }
}
