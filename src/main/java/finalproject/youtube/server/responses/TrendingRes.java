package finalproject.youtube.server.responses;

import finalproject.youtube.server.responses.VideoAttributesRes;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class TrendingRes implements Serializable {
    private int SQLResponse;
    private ArrayList<VideoAttributesRes> trends;
    public TrendingRes(){
        trends = new ArrayList<>();
    }
    public void addVideo(VideoAttributesRes video){
        trends.add(video);
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

}
