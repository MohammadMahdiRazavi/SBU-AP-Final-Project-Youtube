package finalproject.youtube.server.responses;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayListsVideosRes implements Serializable {
    private int SQLResponse;
    private ArrayList<VideoAttributesRes> playlist_videos;

    public PlayListsVideosRes(){
        playlist_videos = new ArrayList<>();
    }

    public void addVideo(VideoAttributesRes video){
        playlist_videos.add(video);
    }

    public ArrayList<VideoAttributesRes> getPlaylist_videos() {
        return playlist_videos;
    }

    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }
    public int getSQLResponse() {
        return SQLResponse;
    }

}
