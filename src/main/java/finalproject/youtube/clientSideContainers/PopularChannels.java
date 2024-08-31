package finalproject.youtube.clientSideContainers;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class PopularChannels {
    private int subscribers, uploadedVideos;
    private String channelsName, channel_id, username_admin;
    private Image channelsImage;

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public void setUploadedVideos(int uploadedVideos) {
        this.uploadedVideos = uploadedVideos;
    }

    public void setChannelsName(String channelsName) {
        this.channelsName = channelsName;
    }

    public void setChannelsImage(byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        this.channelsImage = new Image(inputStream);
    }

    public int getSubscribers() {
        return subscribers;
    }

    public int getUploadedVideos() {
        return uploadedVideos;
    }

    public String getChannelsName() {
        return channelsName;
    }

    public Image getChannelsImage() {
        return channelsImage;
    }

    public static String formatCount(int count) {
        if (count >= 1_000_000) {
            double millions = count / 1_000_000.0;
            if (millions % 1 == 0) {
                return String.format("%.0fM", millions);
            } else {
                return String.format("%.2fM", millions);
            }
        } else if (count >= 1_000) {
            double thousands = count / 1_000.0;
            if (thousands % 1 == 0) {
                return String.format("%.0fK", thousands);
            } else {
                return String.format("%.1fK", thousands);
            }
        } else {
            return String.valueOf(count);
        }
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getUsername_admin() {
        return username_admin;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public void setUsername_admin(String username_admin) {
        this.username_admin = username_admin;
    }
}
