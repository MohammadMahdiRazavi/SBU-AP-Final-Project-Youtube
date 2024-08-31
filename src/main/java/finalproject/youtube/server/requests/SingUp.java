package finalproject.youtube.server.requests;

import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.Request;
import org.json.JSONArray;
import org.json.JSONObject;

public class SingUp implements Request {
    private JSONObject jsonResponse;
    @Override
    public void conductRequest(JSONObject request) {
        String username = request.getString("username");
        String gender = request.getString("gender");
        JSONArray jsonArray = request.getJSONArray("password");
        byte[] password = new byte[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            password[i] = (byte) jsonArray.getInt(i);
        }
        String email = request.getString("email");
        String name = request.getString("name");
        int SQLResponse = DatabaseManger.signUp(username, email, password, gender, name);
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
            jsonResponse.put("response", "Successfully signed up");
        } else if (res == -1) {
            jsonResponse.put("response", "Failed to sign up");
        } else if (res == -2) {
            jsonResponse.put("response", "Username already exist");
        } else if (res == -3) {
            jsonResponse.put("response", "Email already exist");
        }
    }
}
