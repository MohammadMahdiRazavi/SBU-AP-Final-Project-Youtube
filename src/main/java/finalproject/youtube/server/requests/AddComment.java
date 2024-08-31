package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.utility.Utility;
import org.json.JSONObject;

import java.util.UUID;

public class AddComment implements Request {
    private JSONObject jsonResponse;

    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        String comment = request.getString("comment");
        String username = request.getString("username");
        String comment_date = Utility.getDateAndTime();

        int SQLResponse = DatabaseManger.addComment(username, comment, comment_date, video_id);
        makeJson(SQLResponse);
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {
        jsonResponse = new JSONObject();
        if (res == 1){
            jsonResponse.put("response", "Comment added successfully");
        } else if (res == -1) {
            jsonResponse.put("response", "Comment failed");
        }
    }
}
