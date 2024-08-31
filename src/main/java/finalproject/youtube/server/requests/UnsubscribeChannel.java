package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class UnsubscribeChannel implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        String channel_id = request.getString("channel_id");
        int SQLResponse = DatabaseManger.unsubscribeChannel(username, channel_id);
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
            jsonResponse.put("response", "Successfully unsubscribed the channel");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed unsubscribed the channel");
        }
    }
}
