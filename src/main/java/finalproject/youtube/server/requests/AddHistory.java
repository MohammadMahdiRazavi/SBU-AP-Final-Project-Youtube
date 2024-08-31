package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.utility.Utility;
import org.json.JSONObject;

public class AddHistory implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        String video_id = request.getString("video_id");
        String date = Utility.getDateAndTime();
        int SQLResponse = DatabaseManger.addVideoToHistory(username, video_id, date);
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
            jsonResponse.put("response", "History added Successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to add history");
        }
    }
}
