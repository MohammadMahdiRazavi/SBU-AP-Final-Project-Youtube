module finalproject.youtube.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires TrayNotification;
    requires ffmpeg;
    requires javafx.media;

    opens finalproject.youtube.ui to javafx.fxml;
    exports finalproject.youtube.ui;
}