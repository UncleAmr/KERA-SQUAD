package Gui;

public class ThemeManager {
    private static boolean darkMode = false;

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void toggleDarkMode() {
        darkMode = !darkMode;
    }

    public static void setDarkMode(boolean value) {
        darkMode = value;
    }
}
