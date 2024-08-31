package finalproject.youtube.ui;

import finalproject.youtube.File.FileHandler;
import finalproject.youtube.client.Client;
import finalproject.youtube.client.ClientHandler;
import finalproject.youtube.clientSideContainers.PopularChannels;
import finalproject.youtube.clientSideContainers.VideoBox;
import finalproject.youtube.clientSideContainers.YouTubeMix;
import finalproject.youtube.server.responses.ChannelRes;
import finalproject.youtube.server.responses.ChannelsListRes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    private static boolean start = false;
    @FXML private HBox trending;
    @FXML private HBox home;
    @FXML private HBox subscriptions;
    @FXML private HBox history;
    @FXML private HBox search;
    @FXML private HBox liked_videos;
    @FXML private HBox playlists;
    @FXML private Circle avatar;
    @FXML private SVGPath notif_btn;
    @FXML private ScrollPane notification_scroll_pane;
    @FXML private HBox recom_vid_hbox;
    @FXML private VBox pop_channels_vbox;
    @FXML private HBox mix_row_1;
    @FXML private HBox mix_row_2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home.getStyleClass().clear();
        home.getStyleClass().add("sidebar-button-selected");
        if (!start){
            ClientHandler.startConnection();
            start = true;
        }

        if (Client.getUsername().isEmpty() || Client.getProfile() == null) {
            avatar.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("media/avatar/sample.jpg")))));
        } else {
            fillAvatar(Client.getProfile());
        }

        avatar.setOnMouseClicked(e -> {
            if (!Client.getUsername().isEmpty()) {
                HelloApplication.setScene("profile_page.fxml");
            } else {
                HelloApplication.setScene("login.fxml");
            }
        });
        notification_scroll_pane.setVisible(false);
        notif_btn.setOnMouseClicked(e -> {
            //TODO -> have to open notifications when it is clicked
            if (notification_scroll_pane.isVisible()){
                notification_scroll_pane.setVisible(false);
            } else {
                notification_scroll_pane.setVisible(true);
            }
        });
        //This is an example of how to fill the VideoBox objects
        VideoBox videoBox = new VideoBox();
        videoBox.setVideoTitle("Arcane Season 2 - Official Teaser Trailer");
        videoBox.setChannelName("League of Legends");
        videoBox.setVideoDuration(91);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm");
        videoBox.setVideoUploadTime(now.format(formatter));
        byte[] bytes;
        try {
            File file = new File("src/main/resources/finalproject/youtube/ui/media/sample for video box/arcane.jpg");

            FileInputStream fileInputStream = new FileInputStream(file);
            bytes = new byte[(int)file.length()];
            fileInputStream.read(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        videoBox.setPreviewImage(bytes);
        //example ends here
        recom_vid_hbox.getChildren().add(fillVideoBox(videoBox));
        recom_vid_hbox.getChildren().add(fillVideoBox(videoBox));
        recom_vid_hbox.getChildren().add(fillVideoBox(videoBox));
        recom_vid_hbox.getChildren().add(fillVideoBox(videoBox));
        recom_vid_hbox.getChildren().add(fillVideoBox(videoBox));

        ChannelsListRes popularChannel = ClientHandler.getPopularChannels();
        for (ChannelRes i : popularChannel.getChannelsList()){
            PopularChannels popularChannels1 = new PopularChannels();
            popularChannels1.setChannelsName(i.getChannelName());
            popularChannels1.setSubscribers(i.getSub_count());
            popularChannels1.setUploadedVideos(14897);
            popularChannels1.setChannelsImage(i.getProfile_image());
            popularChannels1.setChannel_id(i.getChannel_id());
            popularChannels1.setUsername_admin(i.getAdmin_username());
            pop_channels_vbox.getChildren().add(fillPopularChannel(popularChannels1));
        }

        //This is an example of how to fill the YoutubeMix objects
        YouTubeMix youTubeMix = new YouTubeMix();
        youTubeMix.setMixLabel("Animal");
        youTubeMix.setVideoCount(34);
        byte[] bytes2;
        try {
            File file = new File("src/main/resources/finalproject/youtube/ui/media/sample for mix/squ.jpg");

            FileInputStream fileInputStream = new FileInputStream(file);
            bytes2 = new byte[(int)file.length()];
            fileInputStream.read(bytes2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        youTubeMix.setMixImage(bytes2);
        //example ends here
        mix_row_1.getChildren().add(fillYouTubeMix(youTubeMix));
        mix_row_1.getChildren().add(fillYouTubeMix(youTubeMix));
        mix_row_2.getChildren().add(fillYouTubeMix(youTubeMix));
        mix_row_2.getChildren().add(fillYouTubeMix(youTubeMix));

        //Here we add onMouseClicked for the left panel buttons
        trending.setOnMouseClicked(e -> {
            //TODO -> have to open the trendings here
        });
        home.setOnMouseClicked(e -> {
            //TODO -> have to open the home here
        });
        search.setOnMouseClicked(e -> {
            HelloApplication.setScene("search-page.fxml");
        });
        subscriptions.setOnMouseClicked(e -> {
            //TODO -> have to open the subscriptions here
            if (!Client.getUsername().isEmpty()) {
                HelloApplication.setScene("sub_page.fxml");
            } else {
                AppNotification.showErrorNotification("Failed to get subscriptions", "Please login first");
            }

        });

        history.setOnMouseClicked(e -> {
            //TODO -> have to open the history here
        });
        liked_videos.setOnMouseClicked(e -> {
            //TODO -> have to open the liked_videos here
        });
        playlists.setOnMouseClicked(e -> {
            //TODO -> have to open the playlists here
        });
    }

    private void fillAvatar(byte[] imageBytes){
        //here we should request for users profile photo via another thread
        new Thread(() -> {
            //we request for users image bytes and store it in the blow variable imageBytes
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            Image usersProfile = new Image(inputStream);
            avatar.setFill(new ImagePattern(usersProfile));
            //the profile is set
        }).start();
    }
    private BorderPane fillVideoBox(VideoBox videoBox){
        BorderPane videoBoxBorderPane = new BorderPane();
        videoBoxBorderPane.setPrefHeight(234.4);
        videoBoxBorderPane.setPrefWidth(208.8);
        videoBoxBorderPane.setOnMouseEntered(e -> {
            videoBoxBorderPane.setScaleX(1.1);
            videoBoxBorderPane.setScaleY(1.1);
        });
        videoBoxBorderPane.setOnMouseExited(e -> {
            videoBoxBorderPane.setScaleX(1);
            videoBoxBorderPane.setScaleY(1);
        });
        videoBoxBorderPane.setOnMouseClicked(e -> {
            //TODO -> here i have to open the clicked video
        });
        AnchorPane anchorPane = new AnchorPane();

        Rectangle rectangle = new Rectangle(234, 131.625);
        rectangle.setFill(new ImagePattern(videoBox.getPreviewImage()));
        rectangle.setStrokeWidth(0);

        Label duration = new Label(videoBox.convertSecondsToFormattedTime());
        duration.getStyleClass().add("video-duration");

        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(duration);
        duration.setLayoutX(169);
        duration.setLayoutY(106);

        VBox vBox = new VBox();
        vBox.setPrefWidth(234.4);
        vBox.setPrefHeight(76.8);
        vBox.getStyleClass().add("video-bottom-box");

        Label videoTitle = new Label(videoBox.getVideoTitle());
        videoTitle.setWrapText(true);
        videoTitle.getStyleClass().add("video-label");

        Label channelNameAndDate = new Label(videoBox.getChannelName() + " • " + videoBox.getVideoUploadTime());
        channelNameAndDate.getStyleClass().add("video-channel");

        vBox.getChildren().add(videoTitle);
        vBox.getChildren().add(channelNameAndDate);
        VBox.setMargin(videoTitle ,new Insets(10, 15, 10, 15));
        VBox.setMargin(channelNameAndDate, new Insets(0, 0, 0, 15));

        videoBoxBorderPane.setTop(anchorPane);
        videoBoxBorderPane.setBottom(vBox);

        Rectangle clipRectangle = new Rectangle(234.4, 208.8);
        clipRectangle.setArcHeight(20);
        clipRectangle.setArcWidth(20);
        videoBoxBorderPane.setClip(clipRectangle);

        HBox.setMargin(videoBoxBorderPane, new Insets(0, 15, 0, 0));

        return videoBoxBorderPane;
    }
    private AnchorPane fillPopularChannel(PopularChannels popularChannels){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(440);
        anchorPane.setPrefHeight(52.8);
        anchorPane.getStyleClass().add("pop-chan-box");
        anchorPane.setOnMouseEntered(e -> {
            anchorPane.setScaleX(1.1);
            anchorPane.setScaleY(1.1);
        });
        anchorPane.setOnMouseExited(e -> {
            anchorPane.setScaleX(1);
            anchorPane.setScaleY(1);
        });

        Circle channelsImage = new Circle(17);
        channelsImage.setLayoutX(31);
        channelsImage.setLayoutY(26);
        channelsImage.setFill(new ImagePattern(popularChannels.getChannelsImage()));
        AnchorPane.setTopAnchor(channelsImage, 9.);
        AnchorPane.setBottomAnchor(channelsImage, 9.5);
        AnchorPane.setLeftAnchor(channelsImage, 15.);
        channelsImage.setOnMouseEntered(e -> {
            channelsImage.setStrokeWidth(2);
            channelsImage.setStroke(Color.color(1, 1, 1));
        });
        channelsImage.setOnMouseExited(e -> {
            channelsImage.setStrokeWidth(0);
        });
        channelsImage.setOnMouseClicked(e -> {
            //TODO -> here i have to open the channels page
        });

        Label channelName = new Label(popularChannels.getChannelsName());
        channelName.getStyleClass().add("pop-chan-box-name");
        AnchorPane.setTopAnchor(channelName, 11.);
        AnchorPane.setBottomAnchor(channelName, 26.);
        AnchorPane.setLeftAnchor(channelName, 60.);
        channelName.setLayoutX(60);
        channelName.setLayoutY(11.4);

        Label channelInfo = new Label(PopularChannels.formatCount(popularChannels.getSubscribers())+
                " Subscribers • " + PopularChannels.formatCount(popularChannels.getUploadedVideos()) + " Videos");
        channelInfo.getStyleClass().add("pop-chan-box-info");
        AnchorPane.setTopAnchor(channelInfo, 29.);
        AnchorPane.setBottomAnchor(channelInfo, 8.);
        AnchorPane.setLeftAnchor(channelInfo, 59.);
        channelInfo.setLayoutX(64);
        channelInfo.setLayoutY(30);
        Button button;
        boolean subscribed = ClientHandler.checkIfSubscribed(popularChannels.getChannel_id());
        if (!Client.getUsername().isEmpty()) {
            if (subscribed) {
                button = new Button("SUBSCRIBED");
                button.getStyleClass().add("pop-chan-box-btn-subscribed");
            } else {
                button = new Button("SUBSCRIBE");
                button.getStyleClass().add("pop-chan-box-btn");
            }
        } else {
            button = new Button("SUBSCRIBE");
        }
        button.getStyleClass().add("pop-chan-box-btn");
        button.setLayoutX(360);
        button.setLayoutY(12.8);
        button.setPrefHeight(28.8);
        button.setPrefWidth(82.4);
        AnchorPane.setTopAnchor(button, 13.);
        AnchorPane.setBottomAnchor(button, 13.);
        AnchorPane.setRightAnchor(button, 20.);
        button.setOnAction(event ->{
            if (!Client.getUsername().isEmpty()) {
                String buttonLabel = button.getText();
                if (buttonLabel.equals("SUBSCRIBE")) {
                    button.setText("SUBSCRIBED");
                    button.getStyleClass().clear();
                    button.getStyleClass().add("pop-chan-box-btn-subscribed");
                    ClientHandler.subscribeChannel(popularChannels.getChannel_id());
                } else {
                    button.setText("SUBSCRIBE");
                    button.getStyleClass().clear();
                    button.getStyleClass().add("pop-chan-box-btn");
                    ClientHandler.unsubscribeChannel(popularChannels.getChannel_id());
                }
            } else {
                AppNotification.showErrorNotification("Subscribe channel", "You have to login first!");
            }
        });

        anchorPane.getChildren().add(channelsImage);
        anchorPane.getChildren().add(channelName);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(channelInfo);

        VBox.setMargin(anchorPane, new Insets(0, 20, 10, 30));
        return anchorPane;
    }
    private AnchorPane fillYouTubeMix(YouTubeMix youTubeMix){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(234.4);
        anchorPane.setPrefHeight(113.6);
        HBox.setMargin(anchorPane, new Insets(0, 13.6, 0, 0));
        anchorPane.setOnMouseEntered(e -> {
            anchorPane.setScaleX(1.05);
            anchorPane.setScaleY(1.05);
        });
        anchorPane.setOnMouseExited(e -> {
            anchorPane.setScaleX(1);
            anchorPane.setScaleY(1);
        });
        anchorPane.setOnMouseClicked(e -> {
            //TODO -> have to open the play list when it is clicked
        });

        Rectangle backImage = new Rectangle(234.4, 113.6);
        backImage.setFill(new ImagePattern(youTubeMix.getMixImage()));

        VBox rightVBox = new VBox();
        rightVBox.setPrefHeight(113.6);
        rightVBox.setPrefWidth(89.6);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.getStyleClass().add("youtube-mix-right-panel");
        AnchorPane.setTopAnchor(rightVBox, 0D);
        AnchorPane.setBottomAnchor(rightVBox, 0D);
        AnchorPane.setRightAnchor(rightVBox, 0D);

        Label videoCount = new Label(YouTubeMix.formatVideoCount(youTubeMix.getVideoCount()));
        rightVBox.getChildren().add(videoCount);

        VBox leftVbox = new VBox();
        leftVbox.setPrefHeight(113.6);
        leftVbox.setPrefWidth(144);
        AnchorPane.setTopAnchor(leftVbox, 0D);
        AnchorPane.setBottomAnchor(leftVbox, 0D);
        AnchorPane.setLeftAnchor(leftVbox, 0D);

        Label mixName = new Label(youTubeMix.getMixLabel());
        VBox.setMargin(mixName, new Insets(20, 0, 0, 15));
        mixName.getStyleClass().add("youtube-mix-label");
        leftVbox.getChildren().add(mixName);

        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M17.3,4.4c-7.2,0-13,5.8-13,13s5.8,13,13,13s13-5.8,13-13S24.5,4.4,17.3,4.4z M17.3,27.7C11." +
                "6,27.7,7,23.1,7,17.3S11.6,7,17.3,7s10.4,4.7,10.4,10.4S23.1,27.7,17.3,27.7z M13.5,23.8l10.4-6.5l-10.4" +
                "-6.5V23.8z");
        svgPath.setFill(Color.color(1, 1, 1));
        VBox.setMargin(svgPath, new Insets(35, 0, 0, 15));
        leftVbox.getChildren().add(svgPath);

        anchorPane.getChildren().add(backImage);
        anchorPane.getChildren().add(leftVbox);
        anchorPane.getChildren().add(rightVBox);

        Rectangle clip = new Rectangle(234.4, 113.6);
        clip.setArcHeight(20);
        clip.setArcWidth(20);
        anchorPane.setClip(clip);

        return anchorPane;
    }
    //TODO -> implement a method to show notifications
}