package finalproject.youtube.client;

import finalproject.youtube.File.FileHandler;
import finalproject.youtube.Model.Comment;
import finalproject.youtube.Model.Notification;
import finalproject.youtube.Model.PlayList;
import finalproject.youtube.server.responses.*;
import finalproject.youtube.utility.Utility;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private static Socket userSocket = null;
    private static BufferedReader bufferedReader = null;
    private static BufferedWriter bufferedWriter = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;

    public static void startConnection(){
        try {
            userSocket = new Socket("localhost", 8888);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
            dataInputStream = new DataInputStream(userSocket.getInputStream());
            dataOutputStream = new DataOutputStream(userSocket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(userSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(userSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean sendJsonRequest(JSONObject request){
        try {
            bufferedWriter.write(request.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static String receiveResponse(){
        JSONObject response;
        try {
            String string = bufferedReader.readLine();
            response = new JSONObject(string.substring(string.indexOf("{")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.getString("response");
    }

    public static boolean addComment(String video_id, String comment){
        boolean bol = false;

        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("comment", comment);
        request.put("username", Client.getUsername());
        request.put("code", 100);
        //We send the request here
        bol = sendJsonRequest(request);
        if (bol){
            String response = receiveResponse();
            //we get the response here
            return !response.equals("Comment failed");

        } else {
            return false;
        }
    }

    public static int dislikeVideo(String video_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("username", Client.getUsername());
        request.put("code", 101);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            if (response.equals("Video dislike failed")){
                return -1;
            } else if (response.equals("Video is disliked successfully")){
                return 1;
            } else {
                return 2;
            }

        } else{
            return -1;
        }
    }

    public static boolean addHistory(String video_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("username", Client.getUsername());
        request.put("code", 102);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("History added Successfully");
        } else {
            return false;
        }
    }

    public static int likeVideo(String video_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("username", Client.getUsername());
        request.put("code", 103);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            if (response.equals("Video like failed")){
                return -1;
            } else if (response.equals("Video liked successfully")){
                return 1;
            } else {
                return 2;
            }

        } else{
            return -1;
        }
    }

    public static boolean sendNotification(String channel_id, String content){
        JSONObject request = new JSONObject();
        request.put("channel_id", channel_id);
        request.put("content", content);
        request.put("code", 104);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Notification sent successfully");
        } else{
            return false;
        }
    }

    public static boolean subscribeChannel(String channel_id){
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("channel_id", channel_id);
        request.put("code", 105);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Subscribed successfully");
        } else {
            return false;
        }
    }

    public static boolean addVideoToChannelPlaylist(String video_id, String playlist_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("playlist_id", playlist_id);
        request.put("code", 106);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Video added successfully");
        } else {
            return false;
        }
    }

    public static boolean removeVideoFromChannelPlaylist(String video_id, String playlist_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("playlist_id", playlist_id);
        request.put("code", 140);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Video removed successfully");
        } else {
            return false;
        }
    }
    public static boolean checkDislike(String video_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("username", Client.getUsername());
        request.put("code", 107);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("User has disliked this video");
        } else {
            return false;
        }
    }

    public static boolean checkLike(String video_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("username", Client.getUsername());
        request.put("code", 108);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("User has liked this video");
        } else {
            return false;
        }
    }

    public static int createChannel(String channel_name, String channel_description, byte[] profile_image, String channel_id){
        JSONObject request = new JSONObject();
        request.put("channel_name", channel_name);
        request.put("channel_description", channel_description);
        request.put("profile_image", profile_image);
        request.put("channel_id", channel_id);
        request.put("admin_username", Client.getUsername());
        request.put("code", 109);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            System.out.println(response);
            if (response.equals("Channel created successfully")){
                return 1;
            } else if (response.equals("Channel creation failed")){
                return -1;
            } else {
                return -2;
                //Channel id exist
            }
        } else {
            return -1;
        }
    }

    public static boolean createChannelPlaylist(String name, String channel_id, byte[] cover_image){
        JSONObject request = new JSONObject();
        request.put("name", name);
        request.put("channel_id", channel_id);
        request.put("cover_image", cover_image);
        request.put("code", 110);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Playlist successfully created");
        } else {
            return false;
        }
    }

    public static boolean editChannel(String channel_id, String edit_request, Object new_value){
        JSONObject request = new JSONObject();
        request.put("channel_id", channel_id);
        request.put("edit_request", edit_request);
        request.put("new_value", new_value);
        request.put("code", 111);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("edited channel successfully");
        } else {
            return false;
        }
    }

    public static boolean editChannelPlaylist(String playlist_id, String edit_request, Object new_value){
        JSONObject request = new JSONObject();
        request.put("playlist_id", playlist_id);
        request.put("edit_request", edit_request);
        request.put("new_value", new_value);
        request.put("code", 112);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Playlist is edited successfully");
        } else {
            return false;
        }
    }

    public static boolean editProfile(String username, String edit_request, Object new_value){
        JSONObject request = new JSONObject();
        request.put("username", username);
        request.put("edit_request", edit_request);
        request.put("new_value", new_value);
        request.put("code", 113);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Profile edited successfully");
        } else {
            return false;
        }
    }

    public static boolean editVideo(String video_id, String edit_request, String new_value){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("edit_request", edit_request);
        request.put("new_value", new_value);
        request.put("code", 114);
        if (sendJsonRequest(request)) {
            String response = receiveResponse();
            return response.equals("Video edited successfully");
        } else {
            return false;
        }
    }

    public static ChannelRes getChannel(String channel_id){
        ChannelRes channelRes;
        JSONObject request = new JSONObject();
        request.put("channel_id", channel_id);
        request.put("code", 115);
        sendJsonRequest(request);

        try {
            channelRes = (ChannelRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return channelRes;
    }

    public static PlayListsVideosRes getHistory(){
        PlayListsVideosRes history;
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("code", 116);
        sendJsonRequest(request);
        try {
            history = (PlayListsVideosRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return history;
    }

    public static PlayListsVideosRes getLikedVideos(){
        PlayListsVideosRes liked_videos;
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("code", 117);
        sendJsonRequest(request);

        try {
            liked_videos = (PlayListsVideosRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return liked_videos;
    }

    public static NotificationRes getNotifications(){
        NotificationRes notifications = new NotificationRes();
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("code", 118);
        sendJsonRequest(request);

        try {
            notifications = (NotificationRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return notifications;
    }

    public static PlayListRes getChannelPlaylists(String channel_id){
        PlayListRes channel_playlists;
        JSONObject request = new JSONObject();
        request.put("channel_id", channel_id);
        request.put("code", 119);
        sendJsonRequest(request);

        try {
            channel_playlists = (PlayListRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return channel_playlists;
    }

    public static PlayListsVideosRes getChannelPlayListsVideos(String playlist_id){
        PlayListsVideosRes channels_playlist_videos;
        JSONObject request = new JSONObject();
        request.put("playlist_id", playlist_id);
        request.put("code", 120);
        sendJsonRequest(request);

        try {
            channels_playlist_videos = (PlayListsVideosRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return channels_playlist_videos;
    }

    public static ChannelsListRes getPopularChannels(){
        ChannelsListRes popular_channels;
        JSONObject request = new JSONObject();
        request.put("code", 121);
        sendJsonRequest(request);
        try {
            popular_channels = (ChannelsListRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return popular_channels;
    }

    public static ProfileRes getProfile(){
        ProfileRes profile;
        JSONObject request = new JSONObject();
        request.put("code", 122);
        request.put("username", Client.getUsername());
        sendJsonRequest(request);

        try {
            profile = (ProfileRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return profile;
    }

    public static VideoAttributesRes getVideoAttributes(String video_id){
        VideoAttributesRes videoAttributesRes;
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("code", 126);
        sendJsonRequest(request);

        try {
            videoAttributesRes = (VideoAttributesRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return videoAttributesRes;
    }

    public static ChannelsListRes getSubscriptions(){
        ChannelsListRes subscriptions;
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("code", 124);
        sendJsonRequest(request);

        try {
            subscriptions = (ChannelsListRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return subscriptions;
    }

    public static PlayListsVideosRes searchVideo(String term){
        PlayListsVideosRes search;
        JSONObject request = new JSONObject();
        request.put("term", term);
        request.put("code", 130);
        sendJsonRequest(request);

        try {
            search = (PlayListsVideosRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return search;
    }

    public static ChannelsListRes searchChannel(String term){
        ChannelsListRes search;
        JSONObject request = new JSONObject();
        request.put("term", term);
        request.put("code", 141);
        sendJsonRequest(request);

        try {
            search = (ChannelsListRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return search;
    }

    public static boolean disableNotification(String channel_id){
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("channel_id", channel_id);
        request.put("code", 129);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Successfully disabled notification");
        } else {
            return false;
        }
    }

    public static boolean enableNotification(String channel_id){
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("channel_id", channel_id);
        request.put("code", 142);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Successfully enabled notification");
        } else {
            return false;
        }
    }

    public static boolean unsubscribeChannel(String channel_id){
        JSONObject request = new JSONObject();
        request.put("channel_id", channel_id);
        request.put("username", Client.getUsername());
        request.put("code", 133);
        if (sendJsonRequest(request)){
            return receiveResponse().equals("Successfully unsubscribed the channel");
        } else {
            return false;
        }
    }

    public static boolean createUserPlaylist(String name, byte[] cover_image){
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("name", name);
        request.put("cover_image", cover_image);
        request.put("code", 135);
        if (sendJsonRequest(request)){
            return receiveResponse().equals("Playlist successfully created");
        } else {
            return false;
        }
    }

    public static PlayListRes getUserPlaylist(){
        PlayListRes playListRes;
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("code", 136);
        sendJsonRequest(request);

        try {
            playListRes = (PlayListRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return playListRes;
    }

    public static boolean editUserPlaylist(String playlist_id, String edit_request, Object new_value){
        JSONObject request = new JSONObject();
        request.put("playlist_id", playlist_id);
        request.put("edit_request", edit_request);
        request.put("new_value", new_value);
        request.put("code", 137);
        if (sendJsonRequest(request)){
            return receiveResponse().equals("Playlist is edited successfully");
        } else {
            return false;
        }
    }

    public static boolean addVideoToUserPlaylist(String video_id, String playlist_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("playlist_id", playlist_id);
        request.put("code", 138);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Video added successfully");
        } else {
            return false;
        }
    }

    public static boolean removeVideoFromUserPlaylist(String video_id, String playlist_id){
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("playlist_id", playlist_id);
        request.put("code", 139);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Video removed successfully");
        } else {
            return false;
        }
    }

    public static boolean setNotificationAsSeen(){
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("code", 143);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            return response.equals("Successfully set notifications as seen");
        } else {
            return false;
        }
    }

    public static CommentRes getComments(String video_id){
        CommentRes comments;
        JSONObject request = new JSONObject();
        request.put("video_id", video_id);
        request.put("code", 144);
        sendJsonRequest(request);

        try {
            comments = (CommentRes) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return comments;
    }

    public static int login(String username, String password){
        JSONObject request = new JSONObject();
        request.put("username", username);
        request.put("password", Utility.hashPassword(password));
        request.put("code", 128);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            if (response.equals("You may pass!")){
                return 1;
            } else if (response.equals("You shall not pass")) {
                return 2;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static boolean checkIfSubscribed(String channel_id){
        JSONObject request = new JSONObject();
        request.put("username", Client.getUsername());
        request.put("channel_id", channel_id);
        request.put("code", 145);
        if (sendJsonRequest(request)){
            return receiveResponse().equals("User has subscribed this channel");
        } else {
            return false;
        }
    }

    public static int signUp(String username, String email, String password, String gender, String name){
        JSONObject request = new JSONObject();
        request.put("code", 131);
        request.put("username", username);
        request.put("email", email);
        request.put("password", Utility.hashPassword(password));
        request.put("gender", gender);
        request.put("name", name);
        if (sendJsonRequest(request)){
            String response = receiveResponse();
            if (response.equals("Successfully signed up")){
                return 1;
            } else if (response.equals("Username already exist")) {
                return -2;
            } else if (response.equals("Email already exist")) {
                return -3;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static boolean uploadVideo(String title, String description, byte[] preview_image
            , String channel_id , String category, String extension, byte[] video, int duration){
        JSONObject request = new JSONObject();
        request.put("code", 134);
        request.put("video_id", Utility.creatUUID());
        request.put("description", description);
        request.put("title", title);
        request.put("duration", duration);
        request.put("category", category);
        request.put("channel_id", channel_id);
        request.put("preview_image", preview_image);
        request.put("extension", extension);
        request.put("size", video.length);
        sendJsonRequest(request);
        try {
//            dataOutputStream.writeInt(video.length);
            dataOutputStream.write(video);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return receiveResponse().equals("Successfully uploaded the video");
    }

    public static void getVideo(String video_id, String extension){
        JSONObject request = new JSONObject();
        request.put("code", 125);
        request.put("video_id", video_id);
        request.put("extension", extension);
        sendJsonRequest(request);

        JSONObject response;
//        try {
//            String string = bufferedReader.readLine();
//            response = new JSONObject(string.substring(string.indexOf("{")));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        int size = response.getInt("size");
        try {
            int size = dataInputStream.readInt();
            byte[] video_bytes = new byte[size];
            dataInputStream.readFully(video_bytes, 0, size);
            FileHandler.saveVideo(video_bytes, video_id + extension, FileHandler.PATH_TO_SAVE_CLIENT_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {
        startConnection();
//        byte[] e = {1, -5, 5, 7};
//        String path = "C:\\Users\\varin\\Downloads\\Video\\Arcane Season 2 - Official Teaser Trailer.mp4";
//        System.out.println(uploadVideo("Arcane", "Arcane season 2", e, "channel_id", "animation"
//                , FileHandler.getVideoExtension(path), FileHandler.readVideo(path), FileHandler.getVideosDuration(path)));
        getVideo("e6956a14-4804-44df-8a16-a2e3e66971e9", ".mp4");
    }
}