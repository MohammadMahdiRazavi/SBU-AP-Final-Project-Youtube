package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class CheckDislike implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        String video_id = request.getString("video_id");
        boolean SQLResponse = DatabaseManger.checkForDisLikedVideo(video_id, username);
        makeJson(SQLResponse);
    }

    @Override
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {

    }

    public void makeJson(boolean res) {
        jsonResponse = new JSONObject();
        if (res) {
            jsonResponse.put("response", "User has disliked this video");
        } else {
            jsonResponse.put("response", "User has not disliked this video");
        }
    }
}
