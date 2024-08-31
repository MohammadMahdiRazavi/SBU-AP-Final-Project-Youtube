package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONArray;
import org.json.JSONObject;

public class EditUserPlaylist implements Request {
    private JSONObject jsonResponse;

    @Override
    public void conductRequest(JSONObject request) {
        int SQLResponse = 0;
        String playlist_id = request.getString("playlist_id");
        String edit_request = request.getString("edit_request");
        if (edit_request.equals("update_name")){
            String new_value = request.getString("new_value");
            SQLResponse = DatabaseManger.editUserPlaylistName(new_value, playlist_id);
        } else if (edit_request.equals("update_cover")) {
            JSONArray new_value_json = request.getJSONArray("new_value");
            byte[] new_value = new byte[new_value_json.length()];
            for (int i = 0; i < new_value_json.length(); i++) {
                new_value[i] = (byte) new_value_json.getInt(i);
            }
            SQLResponse = DatabaseManger.editUserPlaylistCover(new_value, playlist_id);
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
        if (res == 1){
            jsonResponse.put("response", "Playlist is edited successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Playlists' edit failed");
        }
    }
}
