package finalproject.youtube.server.responses;

import finalproject.youtube.Model.Notification;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationRes implements Serializable {
    private int SQLResponse;
    private ArrayList<Notification> notifications;

    public NotificationRes(){
        notifications = new ArrayList<>();
    }

    public int getSQLResponse() {
        return SQLResponse;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

    public void addNotification(Notification notification){
        notifications.add(notification);
    }
}
