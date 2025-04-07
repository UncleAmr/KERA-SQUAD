package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage {
    public static void main(String[] args) {
        // Create the landing page frame
        JFrame frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        
        // Create the panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Add padding
        
        // Test case name input
        JLabel testCaseLabel = new JLabel("Please enter your test case name:");
        JTextField testCaseNameField = new JTextField(20);
        testCaseNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, testCaseNameField.getPreferredSize().height));
        
        // Submit button
        JButton submitButton = new JButton("Submit");
        
        // Handle submit action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testCaseName = testCaseNameField.getText().trim();
                if (!testCaseName.isEmpty()) {
                    // Proceed to Main Testing GUI after submitting
                    frame.dispose(); // Close the landing page
                    new MainGUI(testCaseName); // Pass the test case name to the main GUI
                    
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid test case name.");
                }
            }
        });

        // Add components to the panel
        panel.add(testCaseLabel);
        panel.add(testCaseNameField);
        panel.add(submitButton);

        // Set up the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
