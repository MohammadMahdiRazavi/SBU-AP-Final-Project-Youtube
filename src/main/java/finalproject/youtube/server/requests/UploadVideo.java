package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

public class UploadVideo implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String title = request.getString("title");
        String description = request.getString("description");
        String upload_time = Utility.getDateAndTime();
        JSONArray preview_image = request.getJSONArray("preview_image");
        byte[] preview_image_bytes = new byte[preview_image.length()];
        for (int i = 0; i < preview_image.length(); i++) {
            preview_image_bytes[i] = (byte)preview_image.getInt(i);
        }
        String video_category = request.getString("category");
        String url = null;
        String channel_id = request.getString("channel_id");
        String video_id = request.getString("video_id");
        int duration = request.getInt("duration");
        String category = request.getString("category");
        String extension = request.getString("extension");
        int SQLResponse = DatabaseManger.uploadVideo(video_id, title, description, upload_time, preview_image_bytes, url, channel_id, duration, category, extension);
        makeJson(SQLResponse);
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {
        jsonResponse = new JSONObject();
        if (res == 1){
            jsonResponse.put("response", "Successfully uploaded the video");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to upload video");
        }
    }
}
