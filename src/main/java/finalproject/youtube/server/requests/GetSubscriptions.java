package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.ChannelsListRes;
import finalproject.youtube.server.responses.SubscriptionsRes;
import org.json.JSONObject;

public class GetSubscriptions implements Request {
    private JSONObject jsonResponse;
    private ChannelsListRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        response = DatabaseManger.getSubscriptions(username);
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
            jsonResponse.put("response", "Successfully got subscribed channels");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get subscribed channels");
        }
    }

    public ChannelsListRes getResponse() {
        return response;
    }
}
