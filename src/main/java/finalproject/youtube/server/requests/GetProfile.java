package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import finalproject.youtube.server.responses.ProfileRes;
import org.json.JSONObject;

public class GetProfile implements Request {
    private JSONObject jsonResponse;
    private ProfileRes response;

    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        response = DatabaseManger.getProfile(username);
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
        if (res == 1){
            jsonResponse.put("response", "Successfully got the profile");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to get profile");
        }
    }

    public ProfileRes getResponse() {
        return response;
    }
}
