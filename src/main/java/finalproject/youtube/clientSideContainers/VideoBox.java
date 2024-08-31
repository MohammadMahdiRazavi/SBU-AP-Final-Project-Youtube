package finalproject.youtube.clientSideContainers;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class VideoBox {
    private int videoDuration;
    private String videoTitle, channelName, videoUploadTime;
    private Image previewImage;

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setVideoUploadTime(String videoUploadTime) {
        this.videoUploadTime = videoUploadTime;
    }

    public void setPreviewImage(byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        this.previewImage = new Image(inputStream);
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public String getVideoUploadTime() {
        return videoUploadTime;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getChannelName() {
        return channelName;
    }

    public Image getPreviewImage() {
        return previewImage;
    }

    public String convertSecondsToFormattedTime() {
        int totalSeconds = getVideoDuration();
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}