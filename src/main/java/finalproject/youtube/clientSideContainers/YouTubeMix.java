package finalproject.youtube.clientSideContainers;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class YouTubeMix {
    private int videoCount;
    private String mixLabel;
    private Image mixImage;

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public void setMixLabel(String mixLabel) {
        this.mixLabel = mixLabel;
    }

    public void setMixImage(byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        this.mixImage = new Image(inputStream);
    }

    public int getVideoCount() {
        return videoCount;
    }

    public String getMixLabel() {
        return mixLabel;
    }

    public Image getMixImage() {
        return mixImage;
    }

    public static String formatVideoCount(int videoCount){
        int num = videoCount, count = 0;
        for (; num != 0; num /= 10, ++count);
        if (count == 1){
            return "" + videoCount;
        }else {
            return "+" + (videoCount/10) * 10;
        }
    }
}