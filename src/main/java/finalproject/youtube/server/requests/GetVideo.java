package finalproject.youtube.server.requests;

import finalproject.youtube.server.Request;
import org.json.JSONObject;

public class GetVideo implements Request {
    private JSONObject jsonResponse;
    private byte[] response;
    @Override
    public void conductRequest(JSONObject request) {
        String video_id = request.getString("video_id");
        //TODO -> call database here

//        int SQlResponse = ;
//        makeJson(SQlResponse);
    }

    @Override
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    @Override
    public void makeJson(int res) {
        jsonResponse = new JSONObject();
        if (res == 1){
            jsonResponse.put("response", "Successfully got the video");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get the video");
        }
    }

    public byte[] getResponse() {
        return response;
    }
}
