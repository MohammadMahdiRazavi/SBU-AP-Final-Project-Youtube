package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class EditVideo implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        String edit_request = request.getString("edit_request");
        String new_value = request.getString("new_value");
        int SQLResponse = 0;

        if (edit_request.equals("update_title")){
            SQLResponse = DatabaseManger.editVideoTitle(new_value, video_id);
        } else {
            SQLResponse = DatabaseManger.editVideoDescription(new_value, video_id);
        }

        makeJson(SQLResponse);
    }

    @Override
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {
        jsonResponse = new JSONObject();
        if (res == -1){
            jsonResponse.put("response", "Failed to edit video");
        } else if (res == 1) {
            jsonResponse.put("response", "Video edited successfully");
        }
    }
}
