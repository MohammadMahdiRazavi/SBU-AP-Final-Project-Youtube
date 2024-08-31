package finalproject.youtube.server;

import org.json.JSONObject;

public interface Request {
    void conductRequest(JSONObject request);
    JSONObject getJsonResponse();
    void makeJson(int res);
}
