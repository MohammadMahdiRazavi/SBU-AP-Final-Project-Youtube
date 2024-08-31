package finalproject.youtube.server.responses;

import finalproject.youtube.Model.PlayList;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayListRes implements Serializable {
    private int SQLResponse;
    private ArrayList<PlayList> playlists;

    public PlayListRes(){
        playlists = new ArrayList<>();
    }
    public void addPlaylist(PlayList playList){
        playlists.add(playList);
    }

    public ArrayList<PlayList> getPlaylists() {
        return playlists;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }
}
