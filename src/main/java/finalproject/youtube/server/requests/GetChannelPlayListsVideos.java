package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.PlayListsVideosRes;
import org.json.JSONObject;

public class GetChannelPlayListsVideos implements Request {
    private JSONObject jsonResponse;
    private PlayListsVideosRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String playlist_id = request.getString("playlist_id");
        response = DatabaseManger.getChannelPlaylistsVideos(playlist_id);
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
            jsonResponse.put("response", "Successfully got videos");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get videos");
        }
    }

    public PlayListsVideosRes getResponse() {
        return response;
    }

}
