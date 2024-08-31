package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class AddLike implements Request {
    private JSONObject jsonResponse;

    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        String username = request.getString("username");
        int SQLResponse = DatabaseManger.addLikedVideo(video_id, username);
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
            jsonResponse.put("response", "Video liked successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Video like failed");
        } else if (res == 2){
            jsonResponse.put("response", "Removed like");
        }
    }
}
