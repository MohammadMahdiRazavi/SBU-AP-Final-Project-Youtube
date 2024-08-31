package finalproject.youtube.ui;

import finalproject.youtube.client.Client;
import finalproject.youtube.client.ClientHandler;
import finalproject.youtube.server.responses.ChannelRes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ChannelPageController implements Initializable {
    private String channel_id;
    @FXML private Label name_label;
    @FXML private Label id_label;
    @FXML private Circle profile;
    @FXML private Button dashboard_button;
    @FXML private VBox videos_vbox;
    @FXML private VBox playlist_vbox;

    @FXML private void backToMainPage(){
        channel_id = "";
        HelloApplication.setScene("main-page.fxml");
    }
    public void onOpenDashboardClick(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChannelRes channelRes = ClientHandler.getChannel(channel_id);

        profile.setFill(new ImagePattern(new Image(new ByteArrayInputStream(channelRes.getProfile_image()))));
        name_label.setText(channelRes.getChannelName());
        id_label.setText(channelRes.getChannel_id());
        if (!Client.getUsername().isEmpty() && Client.getUsername().equals(channelRes.getAdmin_username())){
            dashboard_button.setDisable(false);
        } else {
            dashboard_button.setDisable(true);
        }
        ClientHandler.getChannelPlaylists(channel_id);

    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }
}