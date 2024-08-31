package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.ChannelRes;
import org.json.JSONObject;

public class GetChannel implements Request {
    private JSONObject jsonResponse;
    private ChannelRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String channel_id = request.getString("channel_id");
        response = DatabaseManger.getChannel(channel_id);
        int SQLResponse = response.getSQLResponse();
        makeJson(SQLResponse);
//        response.setJsonResponse(getJsonResponse());
    }

    @Override
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {
        jsonResponse = new JSONObject();
        if (res == -1){
            jsonResponse.put("response", "Failed to get channel");
        } else if (res == 1) {
            jsonResponse.put("response", "Successfully got channel");
        }
    }

    public ChannelRes getResponse() {
        return response;
    }
}
