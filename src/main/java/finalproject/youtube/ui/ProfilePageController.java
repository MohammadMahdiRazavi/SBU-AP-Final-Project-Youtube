package finalproject.youtube.ui;

import finalproject.youtube.File.FileHandler;
import finalproject.youtube.client.Client;
import finalproject.youtube.client.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfilePageController implements Initializable {
    @FXML private Circle profile;
    @FXML private Label name_label;
    @FXML private Label id_label;
    @FXML private Label email_label;
    @FXML private TextField ch_name_field;
    @FXML private TextField ch_pass_field;
    @FXML private Button theme_btn;
    @FXML private BorderPane main;
    @FXML private Button ch_name_btn;
    @FXML private Button ch_pass_btn;

    @FXML private void backToMainPage(){
        HelloApplication.setScene("main-page.fxml");
    }
    @FXML private void onChoosePictureClick(){
        FileChooser fileChooser = new FileChooser();
        List<String> extensions = Arrays.asList("*.jpg","*.png","*.jpeg");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select profile",extensions);
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Select profile to upload");
        File file = fileChooser.showOpenDialog(null);
        String filePatch = file.getAbsolutePath();
        byte[] image_bytes = FileHandler.readImage(filePatch);
        if (ClientHandler.editProfile(Client.getUsername(), "profile_image", image_bytes)){
            AppNotification.showSuccessNotification("Edit profile", "Successfully edited profile image!");
            fillAvatar(image_bytes);
        } else {
            AppNotification.showErrorNotification("Edit profile", "Failed to edit profile image :(");
        }
    }

    @FXML private void onChangeNameClick(){
        String newName = ch_name_field.getText();
        if (ClientHandler.editProfile(Client.getUsername(), "update_name", newName)){
            AppNotification.showSuccessNotification("Edit profile", "Successfully edited profile name!");
            name_label.setText(newName);
        } else {
            AppNotification.showErrorNotification("Edit profile", "Failed to edit profile name :(");
        }
    }

    @FXML private void onChangePassClick(){
        String newPass = ch_pass_field.getText();
        if (ClientHandler.editProfile(Client.getUsername(), "update_password", newPass)){
            AppNotification.showSuccessNotification("Edit profile", "Successfully changed profile password!");
        } else {
            AppNotification.showErrorNotification("Edit profile", "Failed to edit profile password :(");
        }
    }

    @FXML private void onOpenDashboardClick(){

    }

    @FXML private void onThemeButtonClick(){
        main.getStylesheets().clear();
        if (ThemeController.isOnDarkTheme()) {
            main.getStylesheets().add(String.valueOf(ProfilePageController.class.getResource("profile_page_light.css")));
            ThemeController.changeTheme();
        } else {
            main.getStylesheets().add(String.valueOf(ProfilePageController.class.getResource("profile_page_dark.css")));
            ThemeController.changeTheme();
        }
    }

    @FXML private void checkPassValidation() {
        if (ch_pass_field.getText().length() < 8) {
            ch_pass_btn.setDisable(true);
            ch_pass_field.setStyle("-fx-border-color: #fb133a");
        } else {
            ch_pass_field.setStyle("-fx-border-width: 0");
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).{8,16}$";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(ch_pass_field.getText());
            if (matcher.matches()) {
                ch_pass_btn.setDisable(false);
            }
        }
    }

    @FXML private void checkNameValidation(KeyEvent keyEvent) {
        if (ch_name_field.getText().length() < 3) {
            ch_name_btn.setDisable(true);
            ch_name_field.setStyle("-fx-border-color: #fb133a");
        } else {
            String regex = "^[a-z ,.'-]+$";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(ch_name_field.getText());
            ch_name_field.setStyle("-fx-border-width: 0");
            if (matcher.matches()) {
                ch_name_btn.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ch_name_btn.setDisable(true);
        ch_pass_btn.setDisable(true);


        if (ThemeController.isOnDarkTheme()){
            main.getStylesheets().add(String.valueOf(ProfilePageController.class.getResource("profile_page_dark.css")));
        }
        id_label.setText("@" + Client.getUsername());
        email_label.setText(Client.getEmail());
        name_label.setText(Client.getName());
        if (Client.getProfile() != null){
            fillAvatar(Client.getProfile());
        }
    }

    private void fillAvatar(byte[] imageBytes){
        //here we should request for users profile photo via another thread
        new Thread(() -> {
            //we request for users image bytes and store it in the blow variable imageBytes
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            Image usersProfile = new Image(inputStream);
            profile.setFill(new ImagePattern(usersProfile));
            //the profile is set
        }).start();
    }
}