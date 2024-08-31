package finalproject.youtube.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

public class FileHandler {
    public static final String PATH_TO_SAVE_SERVER_FOLDER = "src\\main\\resources\\finalproject\\youtube\\ServerVideos\\";
    public static final String PATH_TO_SAVE_CLIENT_FOLDER = "src\\main\\resources\\finalproject\\youtube\\ClientVideos\\";

    public static byte[] readVideo(String pathToVideo){
        File video_file = new File(pathToVideo);
        video_file.getName();
        byte[] video_bytes = new byte[(int) video_file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(video_file);
            fileInputStream.read(video_bytes);
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return video_bytes;
    }

    public static String getVideoExtension(String pathToVideo){
        File file = new File(pathToVideo);
        String fileName = file.getName();
        return fileName.substring(fileName.indexOf("."));
    }

    public static String saveVideo(byte[] video, String name, String path){
        File file = new File(path + name);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(video);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path + name;
    }

    public static int getVideosDuration(String pathToFile){
        int duration = 0;
        try {
//            String inputFilePath = "C:\\Users\\varin\\Downloads\\Video\\Arcane Season 2 - Official Teaser Trailer.mp4"; // Replace with your actual video file path

            FFprobe ffprobe = new FFprobe("C:\\Users\\varin\\Documents\\Intellij\\Youtube\\lib\\ffmpeg\\bin\\ffprobe.exe"); // Path to the ffprobe binary
            FFmpegProbeResult probeResult = ffprobe.probe(pathToFile);

            FFmpegFormat format = probeResult.getFormat();
            duration = (int) Math.round(format.duration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return duration;
    }

    public static byte[] readImage(String path){
        File image = new File(path);
        byte[] image_bytes = new byte[(int) image.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(image);
            fileInputStream.read(image_bytes);
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image_bytes;
    }

    public static void main(String[] args) {
//        byte[] videos = readVideo("C:\\Users\\varin\\Downloads\\Video\\Arcane Season 2 - Official Teaser Trailer.mp4");
//        String ext = getVideoExtension("C:\\Users\\varin\\Downloads\\Video\\Arcane Season 2 - Official Teaser Trailer.mp4");
//        String videosPath = saveVideo(videos, "name" + ext, PATH_TO_SAVE_CLIENT_FOLDER);
//        System.out.println(videosPath);
    }

}