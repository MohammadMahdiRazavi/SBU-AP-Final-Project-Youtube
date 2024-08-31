package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.CommentRes;
import org.json.JSONObject;

public class GetComments implements Request {
    private JSONObject jsonResponse;
    private CommentRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        response = DatabaseManger.getComment(video_id);
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
        if (res == -1){
            jsonResponse.put("response", "Failed to get comments");
        } else if (res == 1) {
            jsonResponse.put("response", "Successfully got comments");
        }
    }

    public CommentRes getResponse() {
        return response;
    }
}
