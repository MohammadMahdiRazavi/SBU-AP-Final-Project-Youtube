package finalproject.youtube.Model;

import java.time.LocalDate;

public class User {

    private String username ;
    private String email ;
    private String gender;
    private LocalDate date;
    private String password ;

    /** Constructors */
    public User(String username,String email,String date,String passWord,String gender)
    {
        this.username=username;
        this.setDate(LocalDate.parse(date));
        this.email=email;
        this.password=passWord;
        this.gender=gender;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void changePassword(String password) {
        this.password = password;
        //add database method
    }

    public void changeUsername(String username) {
        this.username = username;
        //add database method
        // you should know this is unique
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}