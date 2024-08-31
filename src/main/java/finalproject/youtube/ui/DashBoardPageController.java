package finalproject.youtube.ui;

import finalproject.youtube.File.FileHandler;
import finalproject.youtube.client.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {

    private byte[] thumbnail;

    private byte[] video_bytes;

    @FXML private ChoiceBox<String> category_box;

    @FXML private Rectangle preview_thumbnail;

    @FXML private TextField title_filed;

    @FXML private TextField title_description;


    @FXML private void backToMainPage(){
        HelloApplication.setScene("main-page.fxml");
    }

    @FXML private void chooseVideo(){
        List<String> extensions = Arrays.asList("*.mp4","*.3gp","*.mkv","*.MP4","*.MKV","*.3GP","*.flv","*.wmv");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select file",extensions);
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Select Video to upload");
        File file = fileChooser.showOpenDialog(null);
        video_bytes = FileHandler.readVideo(file.getPath());
    }

    @FXML private void chooseThumbnail(){
        List<String> extensions = Arrays.asList("*.jpg","*.jpeg","*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose thumbnail");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            thumbnail = FileHandler.readImage(selectedFile.getPath());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(thumbnail);
            preview_thumbnail.setFill(new ImagePattern(new Image(byteArrayInputStream)));
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> choices = FXCollections.observableArrayList(
                "Option A",
                "Option B",
                "Option C"
        );
        category_box.setItems(choices);
    }
}
