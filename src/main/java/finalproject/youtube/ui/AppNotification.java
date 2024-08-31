package finalproject.youtube.ui;

import javafx.application.Platform;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class AppNotification {

    public static void showSuccessNotification(String title, String message){
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(3));
        });
    }

    public static void showErrorNotification(String title, String message){
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndWait();
        });
    }
}