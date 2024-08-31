package finalproject.youtube.ui;

import finalproject.youtube.File.FileHandler;
import finalproject.youtube.Model.Comment;
import finalproject.youtube.client.Client;
import finalproject.youtube.client.ClientHandler;
import finalproject.youtube.server.responses.CommentRes;
import finalproject.youtube.server.responses.VideoAttributesRes;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.media.MediaPlayer.Status.PAUSED;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;

public class VideoPageController implements Initializable {
    private static String current_video_id;
    private boolean liked;
    private boolean disliked;
    private MediaPlayer mediaPlayer;
    @FXML private Label play_rate;
    @FXML private MediaView media_view;
    @FXML private Slider mediaSlider;
    @FXML private Slider volume_slider;
    @FXML private Button like_btn;
    @FXML private Button dislike_btn;
    @FXML private Label video_title;
    @FXML private Label description_content;
    @FXML private Label like_label;
    @FXML private Label dislike_label;
    @FXML private TextField comment_field;
    @FXML private VBox box;
    @FXML private void playVideo(){
        if(mediaPlayer.getStatus() == PAUSED){
            mediaPlayer.play();
        }
    }

    @FXML private void pauseVideo(){
        if(mediaPlayer.getStatus() == PLAYING){
            mediaPlayer.pause();
        }
    }

    @FXML private void fastForward(){
        mediaPlayer.setRate(mediaPlayer.getRate()+0.5);
        play_rate.setText(mediaPlayer.getRate() + "X");
    }

    @FXML private void fastBackward(){
        mediaPlayer.setRate(mediaPlayer.getRate()-0.5);
        play_rate.setText(mediaPlayer.getRate() + "X");
    }

    @FXML private void likeVideo(){
        if (liked){
            like_btn.setId("like_hollow_btn");
            ClientHandler.likeVideo(current_video_id);
            liked = false;
        } else if (disliked){
            dislike_btn.setId("dislike_hollow_btn");
            like_btn.setId("liked_btn");
            ClientHandler.likeVideo(current_video_id);
            disliked = false;
            liked = true;
        } else {
            like_btn.setId("liked_btn");
            liked = true;
            ClientHandler.likeVideo(current_video_id);
        }
    }

    @FXML private void dislikeVideo(){
        if (disliked){
            like_btn.setId("dislike_hollow_btn");
            ClientHandler.dislikeVideo(current_video_id);
            disliked = false;
        } else if (liked) {
            like_btn.setId("like_hollow_btn");
            dislike_btn.setId("disliked_btn");
            liked = false;
            disliked = true;
            ClientHandler.dislikeVideo(current_video_id);
        } else {
            dislike_btn.setId("disliked_btn");
            disliked = true;
            ClientHandler.dislikeVideo(current_video_id);
        }
    }

    @FXML private void backToMainPage(){
        current_video_id = "";
        HelloApplication.setScene("main-page.fxml");
    }

    @FXML private void sendComment(){
        String comment = comment_field.getText();
        if (ClientHandler.addComment(current_video_id, comment)){
            AppNotification.showSuccessNotification("Comment", "Successfully added the comment!");
        } else {
            AppNotification.showErrorNotification("Comment", "Failed to added the comment!");
        }
        fillComments();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillComments();

        VideoAttributesRes videoAttributesRes = ClientHandler.getVideoAttributes(current_video_id);

        video_title.setText(videoAttributesRes.getTitle());
        description_content.setText(videoAttributesRes.getDescription());
        like_label.setText("Likes: " + videoAttributesRes.getLike());
        dislike_label.setText("Dislikes: " + videoAttributesRes.getDislike());

        ClientHandler.getVideo(current_video_id, videoAttributesRes.getExtension());
        String filePath = FileHandler.PATH_TO_SAVE_CLIENT_FOLDER + current_video_id + videoAttributesRes.getExtension();
        File video_file = new File(filePath);
        filePath = video_file.toURI().toString();
        System.out.println(filePath);
        Media media = new Media(filePath);
        mediaPlayer = new MediaPlayer(media);
        media_view.setMediaPlayer(mediaPlayer);
        DoubleProperty width = media_view.fitWidthProperty();
        DoubleProperty height = media_view.fitHeightProperty();
        width.bind(Bindings.selectDouble(media_view.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(media_view.sceneProperty(),"height"));

        volume_slider.setValue(mediaPlayer.getVolume()*100);

        volume_slider.valueProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable obeservable){
                mediaPlayer.setVolume(volume_slider.getValue()/100);
            }
        });

        mediaPlayer.setOnReady(() -> {
            mediaSlider.setMin(0.0);
            mediaSlider.setValue(0.0);
            mediaSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    mediaSlider.setValue(newValue.toSeconds());
                }

            });

            mediaSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(mediaSlider.getValue()));

                }

            });

        });

        mediaPlayer.play();

        if (ClientHandler.checkLike(current_video_id)){
            like_btn.setId("liked_btn");
            liked = true;
        }
        if (ClientHandler.checkDislike(current_video_id)){
            dislike_btn.setId("disliked_btn");
            disliked = true;
        }
    }

    public static void setCurrent_video_id(String current_video_id) {
        VideoPageController.current_video_id = current_video_id;
    }

    public void fillComments(){
        CommentRes commentRes = ClientHandler.getComments(current_video_id);
        ArrayList<Comment> comments = commentRes.getComments();
        for (Comment i : comments) {
            VBox vBox = new VBox();
            vBox.setId("comment_box");
            Label name = new Label(i.getUsername());
            name.setId("comment_name");
            Label content = new Label(i.getContent());
            content.setId("comment_content");
            Label date = new Label(i.getTime());
            date.setId("comment_date");

            VBox.setMargin(name, new Insets(10, 0, 0, 30));
            VBox.setMargin(content, new Insets(0,0,0,30));
            VBox.setMargin(date, new Insets(0,0,10,30));

            vBox.getChildren().add(name);
            vBox.getChildren().add(content);
            vBox.getChildren().add(date);

            VBox.setMargin(vBox, new Insets(0, 50, 10, 30));
            box.getChildren().add(vBox);
        }
    }
}