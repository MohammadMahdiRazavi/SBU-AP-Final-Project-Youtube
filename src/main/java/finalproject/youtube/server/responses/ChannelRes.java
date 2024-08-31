package finalproject.youtube.server.responses;

import org.json.JSONObject;

import java.io.Serializable;

public class ChannelRes implements Serializable {
    private int SQLResponse;
    private String channel_id;
    private String channelName;
    private String description;
    private byte[] profile_image;
    private String admin_username;
    private String date_of_creation;
    private int sub_count;
    //attribute below is for subscriptions
    private boolean send_notif;

    private int video_count;

    public boolean isSend_notif() {
        return send_notif;
    }

    public void setSend_notif(boolean send_notif) {
        this.send_notif = send_notif;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getProfile_image() {
        return profile_image;
    }

    public String getAdmin_username() {
        return admin_username;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfile_image(byte[] profile_image) {
        this.profile_image = profile_image;
    }

    public void setAdmin_username(String admin_username) {
        this.admin_username = admin_username;
    }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public int getSub_count() {
        return sub_count;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public void setSub_count(int sub_count) {
        this.sub_count = sub_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public int getVideo_count() {
        return video_count;
    }
}
