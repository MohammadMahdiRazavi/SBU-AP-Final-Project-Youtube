package finalproject.youtube.server.responses;

import finalproject.youtube.server.responses.VideoAttributesRes;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class LikedVideosRes implements Serializable {
    private int SQLResponse;
    private ArrayList<VideoAttributesRes> liked_video;

    public LikedVideosRes(){
        liked_video = new ArrayList<>();
    }

    public void addVideo(VideoAttributesRes video){
        liked_video.add(video);
    }
    public ArrayList<VideoAttributesRes> getLiked_video() {
        return liked_video;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }
}
