package finalproject.youtube.client;

public class Client {
    private static String username = "";
    private static String name = "";
    private static String email = "";
    private static byte[] profile;

    public static void setUsername(String username) {
        Client.username = username;
    }

    public static void setName(String name) {
        Client.name = name;
    }

    public static void setEmail(String email) {
        Client.email = email;
    }

    public static String getUsername() {
        return username;
    }

    public static String getName() {
        return name;
    }

    public static String getEmail() {
        return email;
    }

    public static byte[] getProfile() {
        return profile;
    }

    public static void setProfile(byte[] profile) {
        Client.profile = profile;
    }
}
