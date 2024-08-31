package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.NotificationRes;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetNotifications implements Request {
    private JSONObject jsonResponse;
    private NotificationRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        response = DatabaseManger.getNotifications(username);
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
            jsonResponse.put("response", "got notifications successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get notifications");
        } else if (res == 2){
          jsonResponse.put("response", "user does not have any notification");
        }
    }

    public NotificationRes getResponse() {
        return response;
    }
}
