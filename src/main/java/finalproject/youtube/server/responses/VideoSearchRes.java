package finalproject.youtube.server.responses;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoSearchRes implements Serializable {
    private int SQLResponse;
    private ArrayList<VideoAttributesRes> search;
    public VideoSearchRes(){
        search = new ArrayList<>();
    }
    public void addSearchedVideo(VideoAttributesRes video){
        search.add(video);
    }

    public ArrayList<VideoAttributesRes> getSearch() {
        return search;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

}
