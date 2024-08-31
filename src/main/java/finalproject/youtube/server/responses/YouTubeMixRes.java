package finalproject.youtube.server.responses;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class YouTubeMixRes implements Serializable {
    private int SQLResponse;
    ArrayList<PlayListRes> youtube_mix;
    public YouTubeMixRes(){
        youtube_mix = new ArrayList<>();
    }
    public void addYouTubeMix(PlayListRes playList){
        youtube_mix.add(playList);
    }
    public ArrayList<PlayListRes> getYoutube_mix() {
        return youtube_mix;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }
}
