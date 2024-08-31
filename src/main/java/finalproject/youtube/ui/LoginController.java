package finalproject.youtube.ui;

import finalproject.youtube.client.Client;
import finalproject.youtube.client.ClientHandler;
import finalproject.youtube.server.responses.ProfileRes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {
    @FXML private PasswordField passwordField;
    @FXML private TextField usernameField;
    @FXML private Button submitLogin;

    @FXML
    public void signup() {
        HelloApplication.setScene("signup.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitLogin.prefWidthProperty().bind(passwordField.widthProperty());
    }

    public void submitLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        int response = ClientHandler.login(username, password);
        if (response == 1){
            AppNotification.showSuccessNotification("Login", "You may pass!");
            Client.setUsername(username);
            ProfileRes profile = ClientHandler.getProfile();
            Client.setProfile(profile.getProfile_picture());
            Client.setName(profile.getName());
            Client.setEmail(profile.getEmail());
            HelloApplication.setScene("main-page.fxml");
        } else if (response == 2) {
            AppNotification.showErrorNotification("Login", "YOU SHALL NOT PASS!");
        } else {
            AppNotification.showErrorNotification("Login", "Failed to login!");
        }
    }

    public void checkUsernameValidation(KeyEvent keyEvent) {
        if (usernameField.getText().length() < 3) {
            submitLogin.setDisable(true);
            usernameField.setStyle("-fx-border-color: #fb133a");
        } else {
            String regex = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(usernameField.getText());
            usernameField.setStyle("-fx-border-width: 0");
            if (matcher.matches() && passwordField.getText().length() >= 8) {
                submitLogin.setDisable(false);
            }
        }
    }

    public void checkPassValidation() {
        if (passwordField.getText().length() < 8) {
            submitLogin.setDisable(true);
            passwordField.setStyle("-fx-border-color: #fb133a");
        } else {
            passwordField.setStyle("-fx-border-width: 0");
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).{8,16}$";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(passwordField.getText());
            if (matcher.matches() && (usernameField.getText().length() >= 3)) {
                submitLogin.setDisable(false);
            }
        }
    }


    @FXML
    private void backToMainPage(){
        HelloApplication.setScene("main-page.fxml");
    }


}