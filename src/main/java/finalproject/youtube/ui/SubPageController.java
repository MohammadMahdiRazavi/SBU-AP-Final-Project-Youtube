package finalproject.youtube.ui;

import finalproject.youtube.client.Client;
import finalproject.youtube.client.ClientHandler;
import finalproject.youtube.clientSideContainers.PopularChannels;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SubPageController implements Initializable {

    @FXML private VBox subed_channels_vbox;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subscriptions.getStyleClass().add("sidebar-button-selected");
//        avatar.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("media/avatar/sample.jpg")))));
        fillAvatar(Client.getProfile());
        avatar.setOnMouseClicked(e -> {
            if (!Client.getUsername().isEmpty()) {
                HelloApplication.setScene("profile_page.fxml");
            } else {
                HelloApplication.setScene("login.fxml");
            }
        });
        notification_scroll_pane.setVisible(false);
        notif_btn.setOnMouseClicked(e -> {
            if (notification_scroll_pane.isVisible()){
                notification_scroll_pane.setVisible(false);
            } else {
                notification_scroll_pane.setVisible(true);
            }
        });

        ChannelsListRes subscription = ClientHandler.getSubscriptions();
        for (ChannelRes i : subscription.getChannelsList()){
            addChannelToUI(i);
        }

        trending.setOnMouseClicked(e -> {
            //TODO -> have to open the trendings here
        });
        home.setOnMouseClicked(e -> {
            HelloApplication.setScene("main-page.fxml");
        });
        subscriptions.setOnMouseClicked(e -> {
            //TODO -> have to open the subscriptions here
        });
        search.setOnMouseClicked(e -> {
            HelloApplication.setScene("search-page.fxml");
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

    private void addChannelToUI(ChannelRes channel){
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
        if (channel.isSend_notif()){
            notif.setId("chan-box-notif-on-btn");
        } else {
            notif.setId("chan-box-notif-off-btn");
        }
        notif.setOnAction(e ->{
            if (channel.isSend_notif()){
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
            if (subscribe.getText().equals("SUBSCRIBED")){
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

        subed_channels_vbox.getChildren().add(anchorPane);
        VBox.setMargin(anchorPane, new Insets(0,0,10,0));
    }
}