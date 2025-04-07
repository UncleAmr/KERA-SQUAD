package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import translator.ActionTranslator;
import executor.CodeExecutor;

public class MainGUI {
    private static final String[] actions = {"Open", "Click", "Type", "Sleep"};
    private static final List<JComboBox<String>> actionDropdowns = new ArrayList<>();
    private static final List<JTextField[]> inputFields = new ArrayList<>();
    private static final List<JPanel> stepPanels = new ArrayList<>();
    
    private String testCaseName;

    // Constructor to initialize the MainGUI with the test case name
    public MainGUI(String testCaseName) {
        this.testCaseName = testCaseName;

        JFrame frame = new JFrame("KERA Squad Automation Testing Framework - " + testCaseName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        // Remove the background panel and just use a regular panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("KERA SQUAD");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel stepsPanel = new JPanel();
        stepsPanel.setOpaque(false);
        stepsPanel.setLayout(new BoxLayout(stepsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(stepsPanel);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JButton addStepButton = new JButton("Add Step");
        addStepButton.addActionListener(e -> {
            JPanel stepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            stepPanel.setOpaque(false);
            JComboBox<String> actionComboBox = new JComboBox<>(actions);
            JTextField inputField1 = new JTextField(30);
            JTextField inputField2 = new JTextField(30);
            inputField2.setVisible(false);
            JButton removeButton = new JButton("Remove");

            actionComboBox.addActionListener(ae -> {
                String selected = (String) actionComboBox.getSelectedItem();
                if ("Type".equals(selected)) {
                    inputField1.setToolTipText("Enter placeholder (e.g. name, id)");
                    inputField2.setToolTipText("Enter text to type");
                    inputField2.setVisible(true);
                } else {
                    inputField1.setToolTipText(null);
                    inputField2.setToolTipText(null);
                    inputField2.setVisible(false);
                }
                stepPanel.revalidate();
                stepPanel.repaint();
            });

            removeButton.addActionListener(event -> {
                stepsPanel.remove(stepPanel);
                actionDropdowns.remove(actionComboBox);
                inputFields.removeIf(pair -> pair[0] == inputField1 && pair[1] == inputField2);
                stepPanels.remove(stepPanel);
                stepsPanel.revalidate();
                stepsPanel.repaint();
            });

            stepPanel.add(actionComboBox);
            stepPanel.add(inputField1);
            stepPanel.add(inputField2);
            stepPanel.add(removeButton);

            stepsPanel.add(stepPanel);
            stepsPanel.revalidate();
            stepsPanel.repaint();

            actionDropdowns.add(actionComboBox);
            inputFields.add(new JTextField[]{inputField1, inputField2});
            stepPanels.add(stepPanel);
        });

        JButton executeAllButton = new JButton("Execute");
        JTextArea outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        outputArea.setOpaque(false);
        outputArea.setBackground(new Color(0, 0, 0, 0));
        outputArea.setForeground(Color.BLACK);

        executeAllButton.addActionListener(e -> {
            List<String> classNames = new ArrayList<>();
            List<String> inputs = new ArrayList<>();

            for (int i = 0; i < actionDropdowns.size(); i++) {
                String action = (String) actionDropdowns.get(i).getSelectedItem();
                String className = ActionTranslator.translate(action);
                String input;

                if ("Type".equals(action)) {
                    String placeholder = inputFields.get(i)[0].getText().trim();
                    String textToType = inputFields.get(i)[1].getText().trim();
                    input = placeholder + "::" + textToType;
                } else {
                    input = inputFields.get(i)[0].getText().trim();
                }

                classNames.add(className);
                inputs.add(input);
            }

            String result = CodeExecutor.executeSteps(classNames, inputs);
            outputArea.setText(result);
        });

        JButton addTestCaseButton = new JButton("Add Test Case");
        addTestCaseButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "New Test Case added.");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(addStepButton);
        buttonPanel.add(executeAllButton);
        buttonPanel.add(addTestCaseButton);

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setOpaque(false);
        outputScrollPane.getViewport().setOpaque(false);

        mainPanel.add(titleLabel);
        mainPanel.add(scrollPane);
        mainPanel.add(buttonPanel);
        mainPanel.add(outputScrollPane);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        addStepButton.doClick(); // Add first step
    }

    
    public static void main(String[] args) {
        
        new LandingPage();
    }
}
