package finalproject.youtube.server.responses;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class VideoAttributesRes implements Serializable {
    private int SQLResponse;
    private String title;
    private String description;
    private int duration;
    private int like;
    private int dislike;
    private byte[] imageBytes;
    private String video_id;
    private String channel_id;
    private String upload_time;
    private String category;
    private String extension;
    private String watched_time; //this on is for history res

    public String getWatched_time() {
        return watched_time;
    }

    public void setWatched_time(String watched_time) {
        this.watched_time = watched_time;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public String getCategory() {
        return category;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
