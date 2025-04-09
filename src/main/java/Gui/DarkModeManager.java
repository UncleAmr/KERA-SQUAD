package Gui;

import javax.swing.*;
import java.awt.*;

public class DarkModeManager {
    public static void applyTheme(Component comp, boolean darkMode) {
        if (comp instanceof JPanel || comp instanceof JScrollPane) {
            comp.setBackground(darkMode ? new Color(30, 30, 30) : Color.WHITE);
        } else if (comp instanceof JLabel || comp instanceof JTextArea || comp instanceof JTextField) {
            comp.setForeground(darkMode ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            comp.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        } else if (comp instanceof JButton || comp instanceof JComboBox) {
            comp.setForeground(Color.WHITE);
            comp.setBackground(darkMode ? new Color(70, 70, 70) : new Color(0, 180, 216));
        }

        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                applyTheme(child, darkMode);
            }
        }
    }
}
