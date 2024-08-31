package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class AddDislike implements Request {
    private JSONObject jsonResponse;

    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        String username = request.getString("username");
        int SQLResponse = DatabaseManger.addDisLikedVideo(video_id, username);
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
            jsonResponse.put("response", "Video is disliked successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Video dislike failed");
        } else if (res == 2) {
            jsonResponse.put("response", "Removed dislike");
        }
    }
}
