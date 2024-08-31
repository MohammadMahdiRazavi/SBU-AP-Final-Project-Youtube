package finalproject.youtube.ui;

public class ThemeController {
    static private boolean onLightTheme = false;
    static private boolean onDarkTheme = true;

    public static void changeTheme(){
        if (onDarkTheme){
            onDarkTheme = false;
            onLightTheme = true;
        } else {
            onDarkTheme = true;
            onLightTheme = false;
        }
    }

    public static boolean isOnLightTheme() {
        return onLightTheme;
    }

    public static boolean isOnDarkTheme() {
        return onDarkTheme;
    }
}