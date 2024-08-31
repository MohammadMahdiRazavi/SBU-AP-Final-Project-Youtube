package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreatChannel implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String channel_name = request.getString("channel_name");
        String channel_description = request.getString("channel_description");
        JSONArray profile_image_json = request.getJSONArray("profile_image");
        byte[] profile_image_bytes = new byte[profile_image_json.length()];
        for (int i = 0; i < profile_image_json.length(); i++) {
            profile_image_bytes[i] = (byte) profile_image_json.getInt(i);
        }
        String date_of_creation = Utility.getDateAndTime();
        String channel_id = request.getString("channel_id");
        String admin_username = request.getString("admin_username");
        int SQLResponse = DatabaseManger.createChannel(channel_name, channel_description, profile_image_bytes, date_of_creation, channel_id, admin_username);
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
            jsonResponse.put("response", "Channel created successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Channel creation failed");
        } else if (res == -2) {
            jsonResponse.put("response", "Channel id exist");
        }
    }
}
