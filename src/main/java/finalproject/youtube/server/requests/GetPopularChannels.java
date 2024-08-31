package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.ChannelsListRes;
import org.json.JSONObject;

public class GetPopularChannels implements Request {
    private JSONObject jsonResponse;
    private ChannelsListRes response;

    @Override
    public void conductRequest(JSONObject request) {
        response = DatabaseManger.getPopularChannels();
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
            jsonResponse.put("response", "Successfully got popular channels");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get popular channels");
        }
    }

    public ChannelsListRes getResponse() {
        return response;
    }
}
