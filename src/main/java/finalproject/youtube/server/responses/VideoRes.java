package finalproject.youtube.server.responses;

import org.json.JSONObject;

import java.io.Serializable;

public class VideoRes implements Serializable {
    private int SQLResponse;
    private byte[] video;

    public int getSQLResponse() {
        return SQLResponse;
    }

    public byte[] getVideo() {
        return video;
    }
}
