package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class AddVideoToChannelPlayList implements Request {
    private JSONObject jsonResponse;

    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        String playlist_id = request.getString("playlist_id");
        int SQLResponse = DatabaseManger.addVideoToChannelPlaylist(video_id, playlist_id);
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
            jsonResponse.put("response", "Video added successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Video add failed");
        }
    }
}
