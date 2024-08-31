package finalproject.youtube.Model;

import java.io.Serializable;

public class Notification implements Serializable {
    private String channel_id;
    private String content;
    private boolean seen;
    private String date;

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getContent() {
        return content;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getDate() {
        return date;
    }
}