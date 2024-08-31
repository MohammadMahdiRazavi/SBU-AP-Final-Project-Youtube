package finalproject.youtube.Model;

import java.io.Serializable;

public class PlayList implements Serializable {
    private String name;
    private int video_count;
    private String playlist_id;
    private String channel_id; //For channel playlists
    private String username; //For user play lists
    private byte[] playlist_cover;

    public void setName(String name) {
        this.name = name;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public void setPlaylist_id(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlaylist_cover(byte[] playlist_cover) {
        this.playlist_cover = playlist_cover;
    }

    public String getName() {
        return name;
    }

    public int getVideo_count() {
        return video_count;
    }

    public String getPlaylist_id() {
        return playlist_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPlaylist_cover() {
        return playlist_cover;
    }
}