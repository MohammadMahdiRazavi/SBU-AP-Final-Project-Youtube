package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.VideoAttributesRes;
import org.json.JSONObject;

public class GetVideoAttributes implements Request {
    private JSONObject jsonResponse;
    private VideoAttributesRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        response = DatabaseManger.getVideoAttribute(video_id);
        int SQLResponse = response.getSQLResponse();
        makeJson(SQLResponse);
    }

    public VideoAttributesRes getResponse() {
        return response;
    }

    @Override
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {
        jsonResponse = new JSONObject();
        if (res == 1){
            jsonResponse.put("response", "Successfully got the videos info");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get the videos info");
        }
    }
}
