package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class CheckIfSubscribed implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        String channel_id = request.getString("channel_id");
        boolean SQLResponse = DatabaseManger.checkForSubscribeChannel(channel_id, username);
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
            jsonResponse.put("response", "User has subscribed this channel");
        } else {
            jsonResponse.put("response", "User has not subscribed this channel");
        }
    }
}
