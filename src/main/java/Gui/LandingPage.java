package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LandingPage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LandingPage::createLandingUI);
    }

    public static void createLandingUI() {
        JFrame frame = new JFrame("Landing Page");
        frame.setSize(500, 300);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 300, 20, 20));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                if (ThemeManager.isDarkMode()) {
                    GradientPaint dark = new GradientPaint(0, 0, new Color(50, 50, 60), 0, getHeight(), new Color(30, 30, 40));
                    g2d.setPaint(dark);
                } else {
                    GradientPaint light = new GradientPaint(0, 0, new Color(235, 240, 255), 0, getHeight(), new Color(210, 220, 255));
                    g2d.setPaint(light);
                }
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel titleLabels = new JLabel("KERA SQUAD");
        titleLabels.setForeground(Color.gray);
        titleLabels.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabels.setBounds(170, 20, 300, 30);

        JLabel titleLabel = new JLabel("Please enter your test case name:");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBounds(50, 60, 400, 30);
        titleLabel.setForeground(ThemeManager.isDarkMode() ? Color.LIGHT_GRAY : Color.DARK_GRAY);

        JTextField testCaseNameField = new JTextField(20);
        testCaseNameField.setBounds(50, 100, 400, 35);
        testCaseNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        testCaseNameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        testCaseNameField.setBackground(ThemeManager.isDarkMode() ? new Color(60, 60, 60) : new Color(245, 245, 245));
        testCaseNameField.setForeground(ThemeManager.isDarkMode() ? Color.WHITE : Color.BLACK);
        testCaseNameField.setCaretColor(ThemeManager.isDarkMode() ? Color.WHITE : Color.BLACK);

        JButton submitButton = new JButton("Submit") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(125, 222, 255),
                        0, getHeight(),
                        new Color(100, 160, 255));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        submitButton.setBounds(50, 150, 400, 40);
        submitButton.setContentAreaFilled(false);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        submitButton.addActionListener(e -> {
            String testCaseName = testCaseNameField.getText().trim();
            if (!testCaseName.isEmpty()) {
                frame.dispose();
                new MainGUI(testCaseName); 
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid test case name.");
            }
        });

        JButton closeButton = new JButton("✕") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 70, 80));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(Font.BOLD, 12f));
                g2.drawString("✕", getWidth() / 2 - 5, getHeight() / 2 + 4);
            }
        };
        closeButton.setBounds(460, 10, 25, 25);
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> System.exit(0));

        JButton themeToggleButton = new JButton(ThemeManager.isDarkMode() ? "☀" : "☾") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bgStart = ThemeManager.isDarkMode() ? new Color(80, 80, 80) : new Color(190, 210, 255);
                Color bgEnd = ThemeManager.isDarkMode() ? new Color(50, 50, 50) : new Color(140, 180, 255);
                GradientPaint gp = new GradientPaint(0, 0, bgStart, 0, getHeight(), bgEnd);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));
                FontMetrics fm = g2.getFontMetrics();
                String icon = getText();
                int textWidth = fm.stringWidth(icon);
                int textHeight = fm.getAscent();
                g2.drawString(icon, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 3);
            }
        };
        themeToggleButton.setBounds(420, 10, 30, 30);
        themeToggleButton.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 1));
        themeToggleButton.setContentAreaFilled(false);
        themeToggleButton.setFocusPainted(false);
        themeToggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themeToggleButton.setToolTipText("Toggle theme");

        themeToggleButton.addActionListener(e -> {
            ThemeManager.toggleDarkMode();
            themeToggleButton.setText(ThemeManager.isDarkMode() ? "☀" : "☾");
            panel.repaint();
            testCaseNameField.setBackground(ThemeManager.isDarkMode() ? new Color(60, 60, 60) : new Color(245, 245, 245));
            testCaseNameField.setForeground(ThemeManager.isDarkMode() ? Color.WHITE : Color.BLACK);
            testCaseNameField.setCaretColor(ThemeManager.isDarkMode() ? Color.WHITE : Color.BLACK);
            titleLabel.setForeground(ThemeManager.isDarkMode() ? Color.LIGHT_GRAY : Color.DARK_GRAY);
        });

        panel.add(titleLabel);
        panel.add(testCaseNameField);
        panel.add(submitButton);
        panel.add(themeToggleButton);
        panel.add(closeButton);
        panel.add(titleLabels);

        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
