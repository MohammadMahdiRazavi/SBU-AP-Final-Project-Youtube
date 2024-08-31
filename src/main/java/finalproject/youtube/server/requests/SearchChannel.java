package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.ChannelsListRes;
import finalproject.youtube.server.responses.PlayListsVideosRes;
import org.json.JSONObject;

public class SearchChannel implements Request {
    private JSONObject jsonResponse;
    private ChannelsListRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String term = request.getString("term");
        response = DatabaseManger.searchChannel(term);
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
            jsonResponse.put("response", "Search complete");
        } else if (res == 2) {
            jsonResponse.put("response", "No match found");
        } else if (res == -1) {
            jsonResponse.put("response", "Search failed");
        }
    }

    public ChannelsListRes getResponse() {
        return response;
    }
}
