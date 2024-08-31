package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONArray;
import org.json.JSONObject;

public class Login implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        JSONArray jsonArray = request.getJSONArray("password");
        byte[] password = new byte[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            password[i] = (byte) jsonArray.getInt(i);
        }
        int SQLResponse = DatabaseManger.login(username, password);
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
            jsonResponse.put("response", "You may pass!");
        }else if (res == -1){
            jsonResponse.put("response", "Failed to connect");
        } else if (res == 2){
            jsonResponse.put("response", "You shall not pass");
        }
    }
}
