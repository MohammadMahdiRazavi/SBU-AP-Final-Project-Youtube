package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.HistoryRes;
import finalproject.youtube.server.responses.PlayListsVideosRes;
import org.json.JSONObject;

public class GetHistory implements Request {
    private JSONObject jsonResponse;
    private PlayListsVideosRes response;
    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        response = DatabaseManger.getHistory(username);
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
            jsonResponse.put("response", "Successfully got users history");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get history");
        }
    }

    public PlayListsVideosRes getResponse() {
        return response;
    }
}
