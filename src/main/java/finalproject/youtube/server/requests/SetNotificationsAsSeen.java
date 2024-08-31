package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.utility.Utility;
import org.json.JSONObject;

public class SetNotificationsAsSeen implements Request {
    private JSONObject jsonResponse;

    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        int SQLResponse = DatabaseManger.setNotificationsAsSeen(username);
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
            jsonResponse.put("response", "Successfully set notifications as seen");
        } else if (res == -1) {
            jsonResponse.put("response", "Notification seen failed");
        }
    }
}
