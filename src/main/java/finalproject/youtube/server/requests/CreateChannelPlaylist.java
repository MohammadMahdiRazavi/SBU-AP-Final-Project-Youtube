package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateChannelPlaylist implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String name = request.getString("name");
        String channel_id = request.getString("channel_id");
        JSONArray cover_json = request.getJSONArray("cover_image");
        byte[] cover_bytes = new byte[cover_json.length()];
        for (int i = 0; i < cover_json.length(); i++) {
            cover_bytes[i] = (byte) cover_json.getInt(i);
        }
        int SQLResponse = DatabaseManger.createChannelPlaylist(name, channel_id, Utility.creatUUID(), cover_bytes);
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
            jsonResponse.put("response", "Playlist successfully created");
        } else if (res == -1) {
            jsonResponse.put("response", "Playlist creation failed");
        }
    }
}
