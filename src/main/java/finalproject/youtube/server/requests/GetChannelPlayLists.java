package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.PlayListRes;
import org.json.JSONObject;

public class GetChannelPlayLists implements Request {
    private JSONObject jsonResponse;
    private PlayListRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String channel_id = request.getString("channel_id");
        response = DatabaseManger.getChannelPlaylists(channel_id);
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
            jsonResponse.put("response", "got playlist successfully");
        } else if(res == -1){
            jsonResponse.put("response", "Failed to get play lists");
        }
    }

    public PlayListRes getResponse() {
        return response;
    }
}
