package finalproject.youtube.database;

import finalproject.youtube.Model.Comment;
import finalproject.youtube.Model.Notification;
import finalproject.youtube.Model.PlayList;
import finalproject.youtube.server.responses.*;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DatabaseManger {
    private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/youtube";
    private static final String SQLUsername = "postgres";
    private static final String password = "mamad8405";

    //TODO -> login is Debugged
    public static int login(String username, byte[] password) {
        try {
            String query = "SELECT * FROM youtube.user WHERE username = ? AND password = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, DatabaseManger.password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setBytes(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean res = resultSet.next();

            //close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();

            if (res){
                return 1;
            } else {
                return 2;
            }
        } catch (SQLException e) {
            return -1;
        }
    }
    //TODO -> signUp is Debugged
    public static int signUp(String username, String email, byte[] password, String gender, String name) {
        //We check here if username exist or not
        if (checkUsername(username)){
            return -2;
            //username exist
        }
        //And we check email here
        if (checkEmail(email)){
            return -3;
            //email exist
        }
        try {
            String query = "INSERT into youtube.user(username, email, password, gender, name) values (?, ?, ?, ?, ?)";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, DatabaseManger.password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setBytes(3, password);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, name);
            preparedStatement.executeUpdate();

            //close connections
            preparedStatement.close();
            connection.close();

            return 1;
        } catch (SQLException e) {
            return -1;
        }

    }

    private static boolean checkUsername(String username){
        try {
            String query = "SELECT username FROM youtube.user WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean res = resultSet.next();

            //close connections
            resultSet.close();
            connection.close();
            preparedStatement.close();

            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkEmail(String email){
        try {
            String query = "SELECT email FROM youtube.user WHERE email = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean res = resultSet.next();

            //close connections
            resultSet.close();
            connection.close();
            preparedStatement.close();

            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO -> uploadVideo is Debugged
    public static synchronized int uploadVideo(String video_id, String title, String description , String upload_time
            , byte[] preview_image, String url , String channel_id , int duration, String category, String extension) {
        try {
            String query = "INSERT INTO youtube.video(video_id, title, description, upload_time, preview_image, url, channel_id, duration, category, extension) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.setString(2,title);
            preparedStatement.setString(3,description);
            preparedStatement.setString(4,upload_time);
            preparedStatement.setBytes(5,preview_image);
            preparedStatement.setString(6,url);
            preparedStatement.setString(7,channel_id);
            preparedStatement.setInt(8,duration);
            preparedStatement.setString(9,category);
            preparedStatement.setString(10, extension);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();

            return 1;
        } catch (SQLException ee){
            return -1;
        }
    }
    //TODO -> creatChannel() is Debugged
    public static synchronized int createChannel(String channel_name, String channel_description, byte[] profile_image, String date_of_creation, String channel_id, String admin_username) {

        if(checkForChannelId(channel_id)){
            System.out.println(channel_id);
            return -2;
        } else {
            String query = "INSERT INTO youtube.channel (channel_name,channel_description,profile_image,date_of_creation,channel_id, admin_username)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            try {
                Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,channel_name);
                preparedStatement.setString(2,channel_description);
                preparedStatement.setBytes(3,profile_image);
                preparedStatement.setString(4,date_of_creation);
                preparedStatement.setString(5,channel_id);
                preparedStatement.setString(6, admin_username);
                preparedStatement.executeUpdate();

                //close connection
                connection.close();
                preparedStatement.close();
            } catch (Exception e){
                return -1;
            }
        }
        return 1;
    }
    //TODO -> checkForChannelId() is Debugged
    public static boolean checkForChannelId(String channel_id){
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            String query = "SELECT channel_id FROM youtube.channel WHERE channel_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, channel_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean bol = resultSet.next();

            //closing connections
            connection.close();
            preparedStatement.close();
            resultSet.close();

            return bol;
        } catch (SQLException e) {
            return false;
        }
    }
    //TODO -> createChannelPlaylist() is Debugged
    public static synchronized int createChannelPlaylist(String name, String channel_id, String playlist_id, byte[] playlist_cover){
        String query = "INSERT INTO youtube.channel_playlist (name,channel_id,playlist_id,playlist_cover) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, channel_id);
            preparedStatement.setString(3, playlist_id);
            preparedStatement.setBytes(4, playlist_cover);
            preparedStatement.executeUpdate();

            //close connections
            preparedStatement.close();
            connection.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> createUserPlaylist() is Debugged
    public static synchronized int createUserPlaylist(String name, String username, String playlist_id, byte[] playlist_cover){
        String query = "INSERT INTO youtube.user_playlist (name,username,playlist_id,playlist_cover) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, playlist_id);
            preparedStatement.setBytes(4, playlist_cover);
            preparedStatement.executeUpdate();

            //close connections
            preparedStatement.close();
            connection.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> addComment() is Debugged
    public static synchronized int addComment(String username, String content, String comment_date, String video_id){
        try {
            String query = "INSERT INTO youtube.comment (username, content, comment_date, video_id) VALUES (?, ?, ?, ?)";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,content);
            preparedStatement.setString(3,comment_date);
            preparedStatement.setString(4,video_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return  1;
        }catch (SQLException e){
            return  -1;
        }
    }
    //TODO -> addDisLikedVideo() is Debugged
    public static synchronized int addDisLikedVideo(String video_id , String username){
        if (checkForDisLikedVideo(video_id, username)){
            //It means that this user has already disliked the video, we have to remove it for him
            if (removeDisLikedVideo(video_id, username) == 1) {
                return 2;
                //We have removed the dislike!
            }
        } if (checkForLikedVideo(video_id, username)) {
            /*
            if this statement is true, that means the @username has already Liked this video
            We have to remove the like and add dislike
            */
            if (removeLikedVideo(video_id , username) != 1){
                //we ran into an error!
                return -1;
            }
            // we didn't run into an error, so we continue to do our work
        }
        String query = "INSERT INTO youtube.disLiked_video (video_id, username) VALUES (?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();

            increaseDisLikeVideo(video_id);
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> removeDisLikedVideo() is Debugged
    public static synchronized int removeDisLikedVideo(String video_id , String username){
        String  query = "DELETE FROM youtube.disLiked_video WHERE username = ? AND video_id = ?";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,video_id);
            preparedStatement.executeUpdate();

            decreaseDisLikeVideo(video_id);

            //close connections
            preparedStatement.close();
            connection.close();
            return 1;
        }catch (SQLException e) {
            return -1;
        }
    }
    //TODO -> addLikeVideo() is Debugged
    public static synchronized int addLikedVideo(String video_id, String username){
        if (checkForLikedVideo(video_id, username)){
            //It means that this user has already liked the video, we have to remove it for him
            if (removeLikedVideo(video_id, username) == 1) {
                return 2;
                //We have removed the like!
            }

        }
        if (checkForDisLikedVideo(video_id, username)) {
            /*
            if this statement is true, that means the @username has already DisLiked this video
            We have to remove the DisLike and add like
            */
            if (removeDisLikedVideo(video_id, username) != 1){
                return -1;
                //We ran into an error!
            }
            //we didn't run into an error, so we continue to do our work
        }
        String  query = "INSERT INTO youtube.Liked_video (username, video_id) VALUES (?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,video_id);
            preparedStatement.executeUpdate();

            increaseLikeVideo(video_id);

            //close connections
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e) {
            return -1;
        }
    }
    //TODO -> removeLikedVideo() is Debugged
    public static synchronized int removeLikedVideo(String video_id , String username){
        String  query = "DELETE FROM youtube.liked_video WHERE username = ? AND video_id = ?";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,video_id);
            preparedStatement.executeUpdate();

            decreaseLikeVideo(video_id);

            //close connections
            preparedStatement.close();
            connection.close();
            return 1;
        }catch (SQLException e) {
            return -1;
        }
    }
    //TODO -> checkForSubscribeChannel() is Debugged
    public static synchronized boolean checkForSubscribeChannel(String channel_id, String username){
        try {
            String query = "SELECT * FROM youtube.subscribers WHERE channel_id = ? AND username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, channel_id);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean bol = resultSet.next();

            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();

            return bol;
        } catch (SQLException e) {
            return false;
        }
    }
    //TODO -> addSubscribers() is Debugged
    public static synchronized int subscribeChannel(String username, String channel_id){
        String query = "INSERT INTO youtube.subscribers (username, channel_id) VALUES (?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,channel_id);
            preparedStatement.executeUpdate();

            increaseSubscribeCount(channel_id);

            //close connections
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> sendNotification() is Debugged
    public static synchronized int sendNotification(String content, String channel_id, String send_date){

        String query = "INSERT INTO youtube.notifications (username, content, channel_id, send_date) " +
                "SELECT s.username, ?, s.channel_id, ? " +
                "FROM youtube.subscribers s " +
                "WHERE s.channel_id = ? AND send_notif = true;";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, send_date);
            preparedStatement.setString(3, channel_id);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return  -1;
        }
    }
    //TODO -> addVideoToHistory() is Debugged
    public static synchronized int addVideoToHistory(String username, String video_id, String time){
        String query = "INSERT INTO youtube.history (username, video_id, time) VALUES (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,video_id);
            preparedStatement.setString(3,time);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();
            return  1;
        }catch (SQLException e) {
            return -1;
        }
    }
    //TODO -> getChannel() is Debugged
    public static synchronized ChannelRes getChannel(String channel){
        String query = "SELECT * FROM youtube.channel WHERE channel_id = ?";
        ChannelRes channelRes = new ChannelRes();
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, channel);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                channelRes.setChannelName(resultSet.getString("channel_name"));
                channelRes.setDescription(resultSet.getString("channel_description"));
                channelRes.setSub_count(resultSet.getInt("subscribe_count"));
                channelRes.setProfile_image(resultSet.getBytes("profile_image"));
                channelRes.setDate_of_creation(resultSet.getString("date_of_creation"));
                channelRes.setChannel_id(resultSet.getString("channel_id"));
                channelRes.setAdmin_username(resultSet.getString("admin_username"));
                channelRes.setSQLResponse(1);
            }

            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            channelRes.setSQLResponse(-1);
        }
        return channelRes;
    }
    //TODO -> getComment() is Debugged
    public static synchronized CommentRes getComment(String video_id){
        CommentRes commentRes = new CommentRes();

        String query = "SELECT * FROM youtube.comment WHERE video_id = ?";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Comment comment = new Comment();

                comment.setUsername(resultSet.getString("username"));
                comment.setContent(resultSet.getString("content"));
                comment.setTime(resultSet.getString("comment_date"));

                commentRes.addComment(comment);
            }

            commentRes.setSQLResponse(1);

            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            commentRes.setSQLResponse(-1);
        }

        return commentRes;
    }
    //TODO -> getVideoAttribute() is Debugged
    public static synchronized VideoAttributesRes getVideoAttribute(String video){
        VideoAttributesRes videoAttributesRes = new VideoAttributesRes();

        try {
            String query = "SELECT * FROM youtube.video WHERE video_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                videoAttributesRes.setVideo_id(resultSet.getString("video_id"));
                videoAttributesRes.setLike(resultSet.getInt("likes"));
                videoAttributesRes.setDislike(resultSet.getInt("dis_like"));
                videoAttributesRes.setTitle(resultSet.getString("title"));
                videoAttributesRes.setDescription(resultSet.getString("description"));
                videoAttributesRes.setUpload_time(resultSet.getString("upload_time"));
                videoAttributesRes.setImageBytes(resultSet.getBytes("preview_image"));
                videoAttributesRes.setChannel_id(resultSet.getString("channel_id"));
                videoAttributesRes.setDuration(resultSet.getInt("duration"));
                videoAttributesRes.setCategory(resultSet.getString("category"));
                videoAttributesRes.setExtension(resultSet.getString("extension"));

                //close connections
                resultSet.close();
                connection.close();
                preparedStatement.close();

                videoAttributesRes.setSQLResponse(1);
            }
        }catch (SQLException e){
            videoAttributesRes.setSQLResponse(-1);
        }

        return videoAttributesRes;
    }
    //TODO -> getNotifications() is Debugged
    public static synchronized NotificationRes getNotifications(String username) {
        NotificationRes notificationRes = new NotificationRes();

        try {
            String query = "SELECT * FROM youtube.notifications WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Notification notification = new Notification();
                notification.setContent(resultSet.getString("content"));
                notification.setSeen(resultSet.getBoolean("seen"));
                notification.setChannel_id(resultSet.getString("channel_id"));
                notification.setDate("send_date");
                notificationRes.addNotification(notification);
            }

            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();

            notificationRes.setSQLResponse(1);
        } catch (SQLException e){
            notificationRes.setSQLResponse(-1);
        }
        return notificationRes;
    }
    //TODO -> getChannelPlaylists() is Debugged
    public static synchronized PlayListRes getChannelPlaylists(String channel){
        PlayListRes playListRes = new PlayListRes();
        try {
            String query = "SELECT * FROM youtube.channel_playlist WHERE channel_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,channel);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                PlayList playList = new PlayList();
                playList.setName(resultSet.getString("name"));
                playList.setVideo_count(resultSet.getInt("video_count"));
                playList.setChannel_id(resultSet.getString("channel_id"));
                playList.setPlaylist_id(resultSet.getString("playlist_id"));
                playList.setPlaylist_cover(resultSet.getBytes("playlist_cover"));
                playListRes.addPlaylist(playList);
            }

            playListRes.setSQLResponse(1);

            //close connection
            resultSet.close();
            connection.close();
            preparedStatement.close();
        }catch (SQLException e){
            playListRes.setSQLResponse(-1);
        }
        return playListRes;
    }
    //TODO -> added and debugged getUserPlaylists()
    public static synchronized PlayListRes getUserPlaylists(String username){
        PlayListRes playListRes = new PlayListRes();
        try {
            String query = "SELECT * FROM youtube.user_playlist WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                PlayList playList = new PlayList();
                playList.setVideo_count(resultSet.getInt("video_count"));
                playList.setName(resultSet.getString("name"));
                playList.setUsername(resultSet.getString("username"));
                playList.setPlaylist_id(resultSet.getString("playlist_id"));
                playList.setPlaylist_cover(resultSet.getBytes("playlist_cover"));
                playListRes.addPlaylist(playList);
            }

            playListRes.setSQLResponse(1);

            //close connection
            resultSet.close();
            connection.close();
            preparedStatement.close();
        }catch (SQLException e){
            playListRes.setSQLResponse(-1);
        }
        return playListRes;
    }
    //TODO -> checkForLikedVideo() is Debugged
    public static boolean checkForLikedVideo(String video_id , String username){
        try {
            String query = "SELECT * FROM youtube.liked_video WHERE video_id = ? AND username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, video_id);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean bol = resultSet.next();

            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();

            return bol;

        } catch (SQLException e) {
            return true;
        }
    }
    //TODO -> checkForDisLikedVideo() is Debugged
    public static boolean checkForDisLikedVideo(String video_id , String username){
        try {
            String query = "SELECT * FROM youtube.disLiked_video WHERE video_id = ? AND username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, video_id);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean bol = resultSet.next();

            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();

            return bol;

        } catch (SQLException ignored) {
        }
        return true;
    }
    //TODO -> getHistory() is Debugged
    public static synchronized PlayListsVideosRes getHistory(String username){
        PlayListsVideosRes historyRes = new PlayListsVideosRes();

        try {
            String query = "SELECT * FROM youtube.history h LEFT JOIN youtube.video v ON h.video_id = v.video_id WHERE h.username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                VideoAttributesRes videoAttributesRes = new VideoAttributesRes();
                videoAttributesRes.setTitle(resultSet.getString("title"));
                videoAttributesRes.setLike(resultSet.getInt("likes"));
                videoAttributesRes.setDislike(resultSet.getInt("dis_like"));
                videoAttributesRes.setDescription(resultSet.getString("description"));
                videoAttributesRes.setVideo_id(resultSet.getString("video_id"));
                videoAttributesRes.setUpload_time(resultSet.getString("upload_time"));
                videoAttributesRes.setImageBytes(resultSet.getBytes("preview_image"));
                videoAttributesRes.setChannel_id(resultSet.getString("channel_id"));
                videoAttributesRes.setDuration(resultSet.getInt("duration"));
                videoAttributesRes.setCategory(resultSet.getString("category"));
                videoAttributesRes.setWatched_time(resultSet.getString("time"));
                videoAttributesRes.setExtension("extension");
                historyRes.addVideo(videoAttributesRes);
            }

            historyRes.setSQLResponse(1);

            //close connections
            resultSet.close();
            connection.close();
            preparedStatement.close();
        }catch (SQLException e){
            historyRes.setSQLResponse(-1);
        }
        return historyRes;
    }
    //TODO -> editProfileName() is Debugged
    public static synchronized int editProfileName(String newName, String username){
        try {
            String query = "UPDATE youtube.user SET name = ? WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,newName);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editProfilePassword() is Debugged, probably need to change newPassword variable type to byte[]
    public static synchronized int editProfilePassword(byte[] newPassword, String username){
        try {
            String query = "UPDATE youtube.user SET password = ? WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1,newPassword);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editProfileImage() is Debugged
    public static synchronized int editProfileImage(byte[] newImage, String username){
        try {
            String query = "UPDATE youtube.user SET image = ? WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1,newImage);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editVideoTitle() is Debugged
    public static synchronized int editVideoTitle(String newTitle, String video_id){
        try {
            String query = "UPDATE youtube.video SET title = ? WHERE video_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,newTitle);
            preparedStatement.setString(2,video_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editVideoDescription() is Debugged
    public static synchronized int editVideoDescription(String newDescription, String video_id){
        try {
            String query = "UPDATE youtube.video SET description = ? WHERE video_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,newDescription);
            preparedStatement.setString(2,video_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editChannelName() is Debugged
    public static synchronized int editChannelName(String new_channel_name, String channel_id){
        try {
            String query = "UPDATE youtube.channel SET channel_name = ? WHERE channel_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,new_channel_name);
            preparedStatement.setString(2,channel_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editChannelDescription() is Debugged
    public static synchronized int editChannelDescription(String new_channel_description, String channel_id){
        try {
            String query = "UPDATE youtube.channel SET channel_description = ? WHERE channel_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,new_channel_description);
            preparedStatement.setString(2,channel_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editChannelProfile() is Debugged
    public static synchronized int editChannelProfile(byte[] new_profile_image, String channel_id){
        try {
            String query = "UPDATE youtube.channel SET profile_image = ? WHERE channel_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1,new_profile_image);
            preparedStatement.setString(2,channel_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editChannelPlaylistName() is Debugged
    public static synchronized int editChannelPlaylistName(String newName, String playlist_id){
        try {
            String query = "UPDATE youtube.channel_playlist SET name = ? WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,newName);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editChannelPlaylistCover() is added and Debugged
    public static synchronized int editChannelPlaylistCover(byte[] newCover, String playlist_id){
        try {
            String query = "UPDATE youtube.channel_playlist SET playlist_cover = ? WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1,newCover);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> addVideoToChannelPlaylist() is Debugged
    public static synchronized int addVideoToChannelPlaylist(String video_id , String playlist_id){
        try {
            String query = "INSERT INTO youtube.channel_playlist_video (video_id, playlist_id) VALUES (?, ?)";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            increaseChannelPlaylistVideoCount(playlist_id);

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> removeVideoFromChannelPlaylist() is added and Debugged
    public static synchronized int removeVideoFromChannelPlaylist(String video_id , String playlist_id){
        try {
            String query = "DELETE FROM youtube.channel_playlist_video WHERE video_id = ? AND playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            decreaseChannelPlaylistVideoCount(playlist_id);

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> increaseChannelPlaylistVideoCount() is Debugged
    public static synchronized void increaseChannelPlaylistVideoCount(String playlist_id){
        try {
            String query = "UPDATE youtube.channel_playlist SET video_count = video_count + 1 WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> decreaseChannelPlaylistVideoCount() is added and Debugged
    public static synchronized void decreaseChannelPlaylistVideoCount(String playlist_id){
        try {
            String query = "UPDATE youtube.channel_playlist SET video_count = video_count - 1 WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> editUserPlaylistName() is Debugged
    public static synchronized int editUserPlaylistName(String newName, String playlist_id){
        try {
            String query = "UPDATE youtube.user_playlist SET name = ? WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,newName);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> editUserPlaylistCover() is added and Debugged
    public static synchronized int editUserPlaylistCover(byte[] newCover, String playlist_id){
        try {
            String query = "UPDATE youtube.user_playlist SET playlist_cover = ? WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBytes(1,newCover);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> addVideoToUserPlaylist() is Debugged
    public static synchronized int addVideoToUserPlaylist(String video_id , String playlist_id){
        try {
            String query = "INSERT INTO youtube.user_playlist_video (video_id, playlist_id) VALUES (?, ?)";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            increaseUserPlaylistVideoCount(playlist_id);

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> removeVideoFromUserPlaylist() is added and Debugged
    public static synchronized int removeVideoFromUserPlaylist(String video_id , String playlist_id){
        try {
            String query = "DELETE FROM youtube.user_playlist_video WHERE video_id = ? AND playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.setString(2,playlist_id);
            preparedStatement.executeUpdate();

            decreaseUserPlaylistVideoCount(playlist_id);

            //close connection
            connection.close();
            preparedStatement.close();
            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> increaseUserPlaylistVideoCount() is Debugged
    public static synchronized void increaseUserPlaylistVideoCount(String playlist_id){
        try {
            String query = "UPDATE youtube.user_playlist SET video_count = video_count + 1 WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> decreaseUserPlaylistVideoCount() is added and Debugged
    public static synchronized void decreaseUserPlaylistVideoCount(String playlist_id){
        try {
            String query = "UPDATE youtube.user_playlist SET video_count = video_count - 1 WHERE playlist_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,playlist_id);
            preparedStatement.executeUpdate();

            //close connection
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> increaseLikeVideo() is Debugged
    public static synchronized void increaseLikeVideo(String video_id){
        String query = "UPDATE youtube.video SET likes = likes + 1 WHERE video_id = ?";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> decreaseLikeVideo() is Debugged
    public static synchronized void decreaseLikeVideo(String video_id){
        String query = "UPDATE youtube.video SET likes = likes - 1 WHERE video_id = ?";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> increaseDisLikeVideo() is Debugged
    public static synchronized void increaseDisLikeVideo(String video_id){
        String query = "UPDATE youtube.video SET dis_like = dis_like + 1 WHERE video_id = ?";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> decreaseDisLikeVideo() is Debugged
    public static synchronized void decreaseDisLikeVideo(String video_id){
        String query = "UPDATE youtube.video SET dis_like = dis_like - 1 WHERE video_id = ?";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,video_id);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();
        }catch (SQLException ignored){
        }
    }
    //TODO -> getChannelPlaylistsVideos() is Debugged
    public static synchronized PlayListsVideosRes getChannelPlaylistsVideos(String playlist_id){
        PlayListsVideosRes playListsVideosRes = new PlayListsVideosRes();
        try {
            String query = "SELECT * FROM youtube.channel_playlist_video cpv LEFT JOIN youtube.video v " +
                    "ON cpv.video_id = v.video_id WHERE playlist_id = ?";

            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,playlist_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                VideoAttributesRes videoAttributesRes = new VideoAttributesRes();
                videoAttributesRes.setVideo_id(resultSet.getString("video_id"));
                videoAttributesRes.setLike(resultSet.getInt("likes"));
                videoAttributesRes.setDislike(resultSet.getInt("dis_like"));
                videoAttributesRes.setTitle(resultSet.getString("title"));
                videoAttributesRes.setDescription(resultSet.getString("description"));
                videoAttributesRes.setUpload_time(resultSet.getString("upload_time"));
                videoAttributesRes.setImageBytes(resultSet.getBytes("preview_image"));
                videoAttributesRes.setChannel_id(resultSet.getString("channel_id"));
                videoAttributesRes.setDuration(resultSet.getInt("duration"));
                videoAttributesRes.setCategory(resultSet.getString("category"));
                videoAttributesRes.setExtension("extension");
                playListsVideosRes.addVideo(videoAttributesRes);
            }

            playListsVideosRes.setSQLResponse(1);

            //close connections
            resultSet.close();
            connection.close();
            preparedStatement.close();

            return playListsVideosRes;
        }catch (SQLException e){
            playListsVideosRes.setSQLResponse(-1);
            return playListsVideosRes;
        }
    }
    //TODO -> getUserPlaylistsVideos() is added and debugged
    public static synchronized PlayListsVideosRes getUserPlaylistsVideos(String playlist_id){
        PlayListsVideosRes playListsVideosRes = new PlayListsVideosRes();
        try {
            String query = "SELECT * FROM youtube.user_playlist_video upv LEFT JOIN youtube.video v " +
                    "ON upv.video_id = v.video_id WHERE playlist_id = ?";

            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,playlist_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                VideoAttributesRes videoAttributesRes = new VideoAttributesRes();
                videoAttributesRes.setVideo_id(resultSet.getString("video_id"));
                videoAttributesRes.setLike(resultSet.getInt("likes"));
                videoAttributesRes.setDislike(resultSet.getInt("dis_like"));
                videoAttributesRes.setTitle(resultSet.getString("title"));
                videoAttributesRes.setDescription(resultSet.getString("description"));
                videoAttributesRes.setUpload_time(resultSet.getString("upload_time"));
                videoAttributesRes.setImageBytes(resultSet.getBytes("preview_image"));
                videoAttributesRes.setChannel_id(resultSet.getString("channel_id"));
                videoAttributesRes.setDuration(resultSet.getInt("duration"));
                videoAttributesRes.setCategory(resultSet.getString("category"));
                videoAttributesRes.setExtension("extension");
                playListsVideosRes.addVideo(videoAttributesRes);
            }

            playListsVideosRes.setSQLResponse(1);

            //close connections
            resultSet.close();
            connection.close();
            preparedStatement.close();

            return playListsVideosRes;
        }catch (SQLException e){
            playListsVideosRes.setSQLResponse(-1);
            return playListsVideosRes;
        }
    }
    //TODO -> LATER -> add a channel named Youtube and make a the youtube mixes there, do it later
    public static synchronized int getYoutubeMix(){
        return 0;
    }
    //TODO -> successfully implemented Popular channels
    public static synchronized ChannelsListRes getPopularChannels(){
        ChannelsListRes channelsListRes = new ChannelsListRes();
        try {
            String query = "SELECT * FROM youtube.channel ORDER BY subscribe_count DESC LIMIT 10";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ChannelRes channelRes = new ChannelRes();
                channelRes.setChannelName(resultSet.getString("channel_name"));
                channelRes.setDescription(resultSet.getString("channel_description"));
                channelRes.setSub_count(resultSet.getInt("subscribe_count"));
                channelRes.setProfile_image(resultSet.getBytes("profile_image"));
                channelRes.setDate_of_creation(resultSet.getString("date_of_creation"));
                channelRes.setChannel_id(resultSet.getString("channel_id"));
                channelsListRes.addChannel(channelRes);
            }

            channelsListRes.setSQLResponse(1);

            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();

            return channelsListRes;
        } catch (SQLException e) {
            channelsListRes.setSQLResponse(-1);
            return channelsListRes;
        }
    }
    //TODO -> LATER -> we need to analyze our client yo recommend video
    public static synchronized int RecommendedVideo(){
        return 0;
    }
    //TODO -> LATER -> we need to add an algorithm to calculate trend rate
    public static synchronized int trending(){
        return 0;
    }
    //TODO -> unsubscribeChannel() is Debugged
    public static synchronized int unsubscribeChannel(String username, String channel_id){
        try {
            String query = "DELETE FROM youtube.subscribers WHERE username = ? AND channel_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,channel_id);
            preparedStatement.executeUpdate();

            decreaseSubscribeCount(channel_id);

            //close connections
            connection.close();
            preparedStatement.close();

            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> searchVideo() is Debugged
    public static synchronized PlayListsVideosRes searchVideo(String term){
        PlayListsVideosRes searchRes = new PlayListsVideosRes();

        List<String> termsList = Arrays.asList(term.split(" "));

        String query = "SELECT * " +
                "FROM youtube.video v " +
                "WHERE (";

        for (int i = 0; i < termsList.size(); i++) {
            query += "lower(v.title) LIKE lower('%" + termsList.get(i) + "%')";
            if (i < termsList.size() - 1) {
                query += " OR ";
            }
        }
        query += ")";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                VideoAttributesRes videoAttributesRes = new VideoAttributesRes();
                videoAttributesRes.setVideo_id(resultSet.getString("video_id"));
                videoAttributesRes.setLike(resultSet.getInt("likes"));
                videoAttributesRes.setDislike(resultSet.getInt("dis_like"));
                videoAttributesRes.setTitle(resultSet.getString("title"));
                videoAttributesRes.setDescription(resultSet.getString("description"));
                videoAttributesRes.setUpload_time(resultSet.getString("upload_time"));
                videoAttributesRes.setImageBytes(resultSet.getBytes("preview_image"));
                videoAttributesRes.setChannel_id(resultSet.getString("channel_id"));
                videoAttributesRes.setDuration(resultSet.getInt("duration"));
                videoAttributesRes.setCategory(resultSet.getString("category"));
                videoAttributesRes.setExtension("extension");
                searchRes.addVideo(videoAttributesRes);
            }

            searchRes.setSQLResponse(1);

            //close connection
            resultSet.close();
            connection.close();
            preparedStatement.close();

            return searchRes;
        } catch (SQLException e) {
            searchRes.setSQLResponse(-1);
            return searchRes;
        }

    }
    //TODO -> added and Debugged searchChannel()
    public static synchronized ChannelsListRes searchChannel(String term){
        ChannelsListRes channelsListRes = new ChannelsListRes();

        List<String> termsList = Arrays.asList(term.split(" "));

        String query = "SELECT * " +
                "FROM youtube.channel c " +
                "WHERE (";

        for (int i = 0; i < termsList.size(); i++) {
            query += "lower(c.channel_name) LIKE lower('%" + termsList.get(i) + "%')";
            if (i < termsList.size() - 1) {
                query += " OR ";
            }
        }
        query += " OR ";
        for (int i = 0; i < termsList.size(); i++) {
            query += "lower(c.channel_id) LIKE lower('%" + termsList.get(i) + "%')";
            if (i < termsList.size() - 1) {
                query += " OR ";
            }
        }

        query += ")";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ChannelRes channelRes = new ChannelRes();
                channelRes.setChannelName(resultSet.getString("channel_name"));
                channelRes.setDescription(resultSet.getString("channel_description"));
                channelRes.setSub_count(resultSet.getInt("subscribe_count"));
                channelRes.setProfile_image(resultSet.getBytes("profile_image"));
                channelRes.setDate_of_creation(resultSet.getString("date_of_creation"));
                channelRes.setChannel_id(resultSet.getString("channel_id"));
                channelsListRes.addChannel(channelRes);
            }

            channelsListRes.setSQLResponse(1);

            channelsListRes.setSQLResponse(1);

            //close connection
            resultSet.close();
            connection.close();
            preparedStatement.close();

            return channelsListRes;
        } catch (SQLException e) {
            channelsListRes.setSQLResponse(-1);
            return channelsListRes;
        }

    }
    //TODO -> getVideoPath() is Debugged
    public static synchronized String getVideoPath(String video_id){
        String url = null;
        try {
            String query = "SELECT url FROM youtube.video WHERE video_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, video_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                url = resultSet.getString("url");
            }
            //close connections
            connection.close();
            preparedStatement.close();
            resultSet.close();

            return url;
        } catch (SQLException e) {
            return url;
        }
    }
    //TODO -> disableNotification() is Debugged
    public static synchronized int disableNotification(String username,String channel_id){
        try {
            String query = "UPDATE youtube.subscribers SET send_notif = false WHERE channel_id = ? AND username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,channel_id);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();

            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> enableNotification() is added and debugged
    public static synchronized int enableNotification(String username,String channel_id){
        try {
            String query = "UPDATE youtube.subscribers SET send_notif = true WHERE channel_id = ? AND username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,channel_id);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();

            return 1;
        }catch (SQLException e){
            return -1;
        }
    }
    //TODO -> added and debugged setNotificationsAsSeen()
    public static synchronized int setNotificationsAsSeen(String username){
        try {
            String query = "UPDATE youtube.notifications SET seen = true WHERE username = ? AND seen = false";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();

            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }
    //TODO -> added and debugged increaseSubscribeCount()
    public static synchronized void increaseSubscribeCount(String channel_id){
        try {
            String query = "UPDATE youtube.channel SET subscribe_count = subscribe_count + 1 WHERE channel_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, channel_id);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO -> added and debugged decreaseSubscribeCount()
    public static synchronized void decreaseSubscribeCount(String channel_id){
        try {
            String query = "UPDATE youtube.channel SET subscribe_count = subscribe_count - 1 WHERE channel_id = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, channel_id);
            preparedStatement.executeUpdate();

            //close connections
            connection.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO -> added and debugged getSubscriptions()
    public static ChannelsListRes getSubscriptions(String username){
        ChannelsListRes subscriptionsRes = new ChannelsListRes();
        try {
            String query = "SELECT * FROM youtube.subscribers ys LEFT JOIN youtube.channel yc ON " +
                    "yc.channel_id = ys.channel_id WHERE ys.username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ChannelRes channelRes = new ChannelRes();
                channelRes.setChannelName(resultSet.getString("channel_name"));
                channelRes.setDescription(resultSet.getString("channel_description"));
                channelRes.setSub_count(resultSet.getInt("subscribe_count"));
                channelRes.setProfile_image(resultSet.getBytes("profile_image"));
                channelRes.setDate_of_creation(resultSet.getString("date_of_creation"));
                channelRes.setChannel_id(resultSet.getString("channel_id"));
                channelRes.setAdmin_username(resultSet.getString("admin_username"));
                channelRes.setSend_notif(resultSet.getBoolean("send_notif"));
                subscriptionsRes.addChannel(channelRes);
            }

            subscriptionsRes.setSQLResponse(1);

            //close connections
            connection.close();
            resultSet.close();
            preparedStatement.close();

            return subscriptionsRes;
        } catch (SQLException e) {
            subscriptionsRes.setSQLResponse(-1);
            return subscriptionsRes;
        }
    }
    //TODO -> added and debugged getLikedVideos()
    public static PlayListsVideosRes getLikedVideos(String username){
        PlayListsVideosRes liked_videos = new PlayListsVideosRes();
        try {
            String query = "SELECT * FROM youtube.liked_video ylv LEFT JOIN youtube.video v on ylv.video_id = v.video_id WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                VideoAttributesRes videoAttributesRes = new VideoAttributesRes();
                videoAttributesRes.setTitle(resultSet.getString("title"));
                videoAttributesRes.setLike(resultSet.getInt("likes"));
                videoAttributesRes.setDislike(resultSet.getInt("dis_like"));
                videoAttributesRes.setDescription(resultSet.getString("description"));
                videoAttributesRes.setVideo_id(resultSet.getString("video_id"));
                videoAttributesRes.setUpload_time(resultSet.getString("upload_time"));
                videoAttributesRes.setImageBytes(resultSet.getBytes("preview_image"));
                videoAttributesRes.setChannel_id(resultSet.getString("channel_id"));
                videoAttributesRes.setDuration(resultSet.getInt("duration"));
                videoAttributesRes.setCategory(resultSet.getString("category"));
                videoAttributesRes.setExtension("extension");
                liked_videos.addVideo(videoAttributesRes);
            }

            liked_videos.setSQLResponse(1);

            //close connections
            preparedStatement.close();
            connection.close();
            resultSet.close();

        } catch (SQLException e) {
            liked_videos.setSQLResponse(-1);
        }

        return liked_videos;
    }
    //TODO ->  added and debugged getProfile()
    public static ProfileRes getProfile(String username){
        ProfileRes profile = new ProfileRes();
        try {
            String query = "SELECT * FROM youtube.user WHERE username = ?";
            Connection connection = DriverManager.getConnection(jdbcUrl, SQLUsername, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                profile.setName(resultSet.getString("name"));
                profile.setEmail(resultSet.getString("email"));
                profile.setProfile_picture(resultSet.getBytes("image"));
                //TODO -> if the profile picture is null, i have to send the default value
                profile.setUsername("username");
            }

            //close connections
            resultSet.close();
            connection.close();
            preparedStatement.close();

        } catch (SQLException e) {
            profile.setSQLResponse(-1);
        }
        return profile;
    }
    public static void main(String[] args) {
//        enableNotification("jack", "ch");
//        System.out.println(decreaseUserPlaylistVideoCount("playlist_id"));
//        2024/07/06  15:21
//        2024/07/06  15:22
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm");
//        String dateString1 = "2024/07/06  15:21";
//        String dateString2 = "2024/07/06  15:22";
//
//        LocalDateTime date1 = LocalDateTime.parse(dateString1, formatter);
//        LocalDateTime date2 = LocalDateTime.parse(dateString2, formatter);
//
//        if (date1.isBefore(date2)) {
//            System.out.println("Date 1 is older than Date 2.");
//        } else if (date1.isAfter(date2)) {
//            System.out.println("Date 2 is older than Date 1.");
//        } else {
//            System.out.println("Both dates are the same.");
//        }
//        CommentRes commentRes = getComment("test");
//        ArrayList<Comment> comments = commentRes.getComments();
//        for (Comment i : comments){
//            System.out.println(i.getUsername());
//            System.out.println(i.getContent());
//            System.out.println(i.getTime());
//        }
    }
}
