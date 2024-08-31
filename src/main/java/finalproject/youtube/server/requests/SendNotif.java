package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.utility.Utility;
import org.json.JSONObject;

public class SendNotif implements Request {
    private JSONObject jsonResponse;

    @Override
    public void conductRequest(JSONObject request) {
        String content = request.getString("content");
        String channel_id = request.getString("channel_id");
        String send_date = Utility.getDateAndTime();
        int SQLResponse = DatabaseManger.sendNotification(content, channel_id, send_date);
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
            jsonResponse.put("response", "Notification sent successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Notification failed");
        }
    }

}
