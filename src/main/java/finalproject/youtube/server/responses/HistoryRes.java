package finalproject.youtube.server.responses;

import finalproject.youtube.server.responses.VideoAttributesRes;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class HistoryRes implements Serializable {
    private int SQLResponse;
    private ArrayList<VideoAttributesRes> watched_video;

    public HistoryRes(){
        watched_video = new ArrayList<>();
    }

    public void addVideo(VideoAttributesRes video){
        watched_video.add(video);
    }

    public ArrayList<VideoAttributesRes> getWatched_video() {
        return watched_video;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

}
