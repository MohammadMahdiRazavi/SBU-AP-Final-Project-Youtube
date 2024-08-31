package finalproject.youtube.server.responses;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ChannelsListRes implements Serializable {
    private int SQLResponse;
    private ArrayList<ChannelRes> channels;


    public ChannelsListRes(){
        channels = new ArrayList<>();
    }
    public void addChannel(ChannelRes channel){
        channels.add(channel);
    }
    public ArrayList<ChannelRes> getChannelsList() {
        return channels;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

}
