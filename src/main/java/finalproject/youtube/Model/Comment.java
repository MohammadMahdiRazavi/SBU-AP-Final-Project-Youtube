package finalproject.youtube.Model;

import java.io.Serializable;

public class Comment implements Serializable {

    private String content;
    private String username;
    private String time;

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTime(String time) {
        this.time = time;
    }
}