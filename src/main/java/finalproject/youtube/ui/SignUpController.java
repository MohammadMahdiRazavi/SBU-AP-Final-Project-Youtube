package finalproject.youtube.ui;


import finalproject.youtube.client.ClientHandler;

import finalproject.youtube.server.responses.ProfileRes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpController {
    @FXML
    Button submitSignUp;
    @FXML
    TextField emailField;
    @FXML
    TextField nameField;
    @FXML
    PasswordField passwordField;
    @FXML
    ToggleGroup toggle;
    @FXML
    TextField usernameField;
    @FXML
    VBox vBox;

    @FXML
    private void backToMainPage(){
        HelloApplication.setScene("main-page.fxml");
    }
    @FXML
    public void initialize() {
        submitSignUp.prefWidthProperty().bind(emailField.widthProperty());
    }

    @FXML
    public void login() {
        HelloApplication.setScene("login.fxml");
    }

    public void checkEmailValidation() {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailField.getText());
        if (!matcher.matches()) {
            submitSignUp.setDisable(true);
            emailField.setStyle("-fx-border-color: #fb133a");
        } else {
            if (passwordField.getText().length() >= 8 && usernameField.getText().length() >= 3)
                submitSignUp.setDisable(false);
            emailField.setStyle("-fx-border-width: 0");
        }
    }
    @FXML
    private void checkNameValidation(){
        if (nameField.getText().length() < 3) {
            submitSignUp.setDisable(true);
            nameField.setStyle("-fx-border-color: #fb133a");
        } else {
            String regex = "^[a-z ,.'-]+$";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nameField.getText());
            if (matcher.matches() && passwordField.getText().length() >= 8) submitSignUp.setDisable(false);
            nameField.setStyle("-fx-border-width: 0");
        }
    }

    public void checkPassValidation() {
        if (passwordField.getText().length() < 8) {
            submitSignUp.setDisable(true);
            passwordField.setStyle("-fx-border-color: #fb133a");
        } else {
            passwordField.setStyle("-fx-border-width: 0");
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).{8,16}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(passwordField.getText());
            if (matcher.matches() && usernameField.getText().length() >= 3)
                submitSignUp.setDisable(false);
        }
    }

    public void submitSignUp(ActionEvent event) {
        String gender = ((RadioButton) toggle.getSelectedToggle()).getText();
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        int response = ClientHandler.signUp(username, email, password, gender, name);
        if (response == 1){
            AppNotification.showSuccessNotification("Sign up", "Successfully signed up!");
        } else if (response == -2) {
            AppNotification.showErrorNotification("Sign up", "Username already exist");
        } else if (response == -3) {
            AppNotification.showErrorNotification("Sign up", "Email already exist");
        } else {
            AppNotification.showErrorNotification("Sign up", "Failed to sign up");
        }
    }

    public void checkUsernameValidation(KeyEvent keyEvent) {
        if (usernameField.getText().length() < 3) {
            submitSignUp.setDisable(true);
            usernameField.setStyle("-fx-border-color: #fb133a");
        } else {
            String regex = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(usernameField.getText());
            if (matcher.matches() && passwordField.getText().length() >= 8) submitSignUp.setDisable(false);
            usernameField.setStyle("-fx-border-width: 0");
        }
    }
}