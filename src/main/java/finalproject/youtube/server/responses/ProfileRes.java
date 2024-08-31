package finalproject.youtube.server.responses;

import java.io.Serializable;

public class ProfileRes implements Serializable {
    private int SQLResponse;
    private byte[] profile_picture;
    private String name;
    private String username;
    private String email;


    public byte[] getProfile_picture() {
        return profile_picture;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setProfile_picture(byte[] profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getSQLResponse() {
        return SQLResponse;
    }
    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }

}
