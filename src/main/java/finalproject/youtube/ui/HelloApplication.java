package finalproject.youtube.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("YouTube");
        stage.setResizable(false);
        stage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("media/AppIcon/youtube-logo-5.png")));
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setScene(String url)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(url));
            Scene newScene = new Scene(fxmlLoader.load());
            primaryStage.setScene(newScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}