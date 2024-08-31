package finalproject.youtube.ui;

import finalproject.youtube.client.Client;
import finalproject.youtube.client.ClientHandler;
import finalproject.youtube.clientSideContainers.PopularChannels;
import finalproject.youtube.server.responses.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    @FXML private HBox home;
    @FXML private HBox subscriptions;
    @FXML private HBox history;
    @FXML private HBox search;
    @FXML private HBox liked_videos;
    @FXML private HBox playlists;
    @FXML private Circle avatar;
    @FXML private SVGPath notif_btn;
    @FXML private ScrollPane notification_scroll_pane;
    @FXML private ToggleGroup group;
    @FXML private TextField search_field;
    @FXML private VBox result_box;

    @FXML private void doSearch(){
        String search_type = ((RadioButton) group.getSelectedToggle()).getText();
        String term = search_field.getText();
        if (term.isEmpty()){
            return;
        }
        if (Objects.equals(search_type, "Video")){
            result_box.getChildren().clear();
            PlayListsVideosRes videoSearch = ClientHandler.searchVideo(term);
            fillVideoSearch(videoSearch.getPlaylist_videos());
        } else {
            result_box.getChildren().clear();
            ChannelsListRes channelSearch = ClientHandler.searchChannel(term);
            fillChannelSearch(channelSearch.getChannelsList());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        search.getStyleClass().clear();
        search.getStyleClass().add("sidebar-button-selected");
        subscriptions.setOnMouseClicked(e -> {
            //TODO -> have to open the subscriptions here
            if (!Client.getUsername().isEmpty()) {
                HelloApplication.setScene("sub_page.fxml");
            } else {
                AppNotification.showErrorNotification("Failed to get subscriptions", "Please login first");
            }

        });
        home.setOnMouseClicked(e -> {
            HelloApplication.setScene("main-page.fxml");
        });

    }

    private void fillVideoSearch(ArrayList<VideoAttributesRes> videoAttributesRes){
        for (VideoAttributesRes i : videoAttributesRes) {
            HBox hBox = new HBox();
            hBox.setId("video-box");

            VBox.setMargin(hBox, new Insets(0,0,10,0));

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(384);
            anchorPane.setPrefHeight(216);

            Label duration = new Label(convertSecondsToFormattedTime(i.getDuration()));
            duration.setLayoutX(306);
            duration.setLayoutY(188);
            duration.setId("video-duration");

            Rectangle thumbnail = new Rectangle(384, 216);
            thumbnail.setArcWidth(20);
            thumbnail.setArcHeight(20);
            thumbnail.setStrokeWidth(0);

            anchorPane.getChildren().add(thumbnail);
            anchorPane.getChildren().add(duration);

            VBox vBox = new VBox();
            HBox.setMargin(vBox, new Insets(0, 0, 0, 10));

            Label title = new Label(i.getTitle());
            title.setId("video_title");

            Label date = new Label(i.getUpload_time());
            VBox.setMargin(date, new Insets(0, 0, 5, 0));
            date.setId("date_label");

            ChannelRes channelRes = ClientHandler.getChannel(i.getChannel_id());

            HBox channel = new HBox();
            VBox.setMargin(channel, new Insets(0, 0, 10, 0));

            Circle circle = new Circle(20);
            circle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(channelRes.getProfile_image()))));
            circle.setStrokeWidth(0);

            Label channel_name = new Label(channelRes.getChannelName());
            HBox.setMargin(channel_name, new Insets(0, 0, 0, 10));
            channel_name.setId("channel_name");

            channel.getChildren().add(circle);
            channel.getChildren().add(channel_name);

            Label description = new Label(i.getDescription());
            description.setId("description");

            vBox.getChildren().add(title);
            vBox.getChildren().add(date);
            vBox.getChildren().add(channel);
            vBox.getChildren().add(description);

            hBox.getChildren().add(anchorPane);
            hBox.getChildren().add(vBox);

            result_box.getChildren().add(hBox);

            hBox.setOnMouseClicked(e -> {
                VideoPageController.setCurrent_video_id(i.getVideo_id());
                HelloApplication.setScene("video-page.fxml");
            });
        }
    }

    private void fillChannelSearch(ArrayList<ChannelRes> channelRes){
        for (ChannelRes channel : channelRes) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(946.4, 140);
            anchorPane.setId("chan-box");

            Circle circle = new Circle(50);
            circle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(channel.getProfile_image()))));

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER_LEFT);
            Label name = new Label(channel.getChannelName());
            name.setId("chan-box-name");
            Label sub = new Label(PopularChannels.formatCount(channel.getSub_count()));
            sub.setId("chan-box-info");
            Label vid = new Label(String.valueOf(channel.getVideo_count()));
            vBox.getChildren().add(name);
            vBox.getChildren().add(sub);
            vBox.getChildren().add(vid);


            Button notif = new Button();
            if (channel.isSend_notif()) {
                notif.setId("chan-box-notif-on-btn");
            } else {
                notif.setId("chan-box-notif-off-btn");
            }
            notif.setOnAction(e -> {
                if (channel.isSend_notif()) {
                    channel.setSend_notif(false);
                    notif.setId("chan-box-notif-off-btn");
                    ClientHandler.disableNotification(channel.getChannel_id());
                } else {
                    channel.setSend_notif(true);
                    notif.setId("chan-box-notif-on-btn");
                    ClientHandler.enableNotification(channel.getChannel_id());
                }
            });
            notif.setScaleY(1.5);
            notif.setScaleX(1.5);

            Button subscribe = new Button("SUBSCRIBED");
            subscribe.setId("chan-box-btn-subscribed");
            subscribe.setOnAction(e -> {
                if (subscribe.getText().equals("SUBSCRIBED")) {
                    ClientHandler.unsubscribeChannel(channel.getChannel_id());
                    subscribe.setId("chan-box-btn");
                    subscribe.setText("SUBSCRIBE");
                } else {
                    ClientHandler.subscribeChannel(channel.getChannel_id());
                    subscribe.setId("chan-box-btn-subscribed");
                    subscribe.setText("SUBSCRIBED");
                }
            });


            anchorPane.getChildren().add(circle);
            anchorPane.getChildren().add(vBox);
            anchorPane.getChildren().add(notif);
            anchorPane.getChildren().add(subscribe);

            circle.setLayoutX(70);
            circle.setLayoutY(70);
            AnchorPane.setTopAnchor(circle, 20.);
            AnchorPane.setBottomAnchor(circle, 20.0);

            vBox.setLayoutX(135);
            vBox.setLayoutY(15);
            AnchorPane.setTopAnchor(vBox, 15.);
            AnchorPane.setBottomAnchor(vBox, 14.39);

            notif.setLayoutX(738);
            notif.setLayoutY(55);
            AnchorPane.setTopAnchor(notif, 55.);
            AnchorPane.setBottomAnchor(notif, 54.4);

            subscribe.setLayoutX(780);
            subscribe.setLayoutY(51);
            AnchorPane.setTopAnchor(subscribe, 50.);
            AnchorPane.setBottomAnchor(subscribe, 50.0);

            result_box.getChildren().add(anchorPane);
            VBox.setMargin(anchorPane, new Insets(0, 0, 10, 0));
        }
    }

    public String convertSecondsToFormattedTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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

}