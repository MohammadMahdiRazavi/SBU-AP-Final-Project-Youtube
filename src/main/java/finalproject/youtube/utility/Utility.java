package finalproject.youtube.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utility {
    public static String getDateAndTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm");
        return now.format(formatter);
    }

    public static String creatUUID(){
        return UUID.randomUUID().toString();
    }

    public static byte[] hashPassword(String password) {
        try {
            MessageDigest md =  MessageDigest.getInstance("SHA3-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return digest;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}