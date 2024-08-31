package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.LikedVideosRes;
import finalproject.youtube.server.responses.PlayListsVideosRes;
import org.json.JSONObject;

public class GetLikedVideos implements Request {
    private JSONObject jsonResponse;
    private PlayListsVideosRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        response = DatabaseManger.getLikedVideos(username);
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
            jsonResponse.put("response", "Successfully got users liked videos");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get liked videos");
        }
    }

    public PlayListsVideosRes getResponse() {
        return response;
    }
}
