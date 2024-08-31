package finalproject.youtube.server.responses;

import finalproject.youtube.server.responses.VideoAttributesRes;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class RecommendedVideosRes implements Serializable {
    private int SQLResponse;
    private ArrayList<VideoAttributesRes> recommended_videos;
    public RecommendedVideosRes(){
        recommended_videos = new ArrayList<>();
    }
    public void addVideo(VideoAttributesRes video){
        recommended_videos.add(video);
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }
}
