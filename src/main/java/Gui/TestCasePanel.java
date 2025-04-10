package Gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestCasePanel {
    private final JPanel testCaseContainer;
    private final JPanel stepsPanel;
    private final List<JComboBox<String>> actionDropdowns;
    private final List<JComponent[]> inputComponents;
    private final String testCaseName;

    public TestCasePanel(String testCaseTitle) {
        this.testCaseName = testCaseTitle; 
        testCaseContainer = new JPanel();
        testCaseContainer.setLayout(new BorderLayout());
        testCaseContainer.setOpaque(false);
        testCaseContainer.setBorder(BorderFactory.createTitledBorder(testCaseTitle));
        TitledBorder border = BorderFactory.createTitledBorder(testCaseTitle);
        border.setTitleColor(Color.gray); // <- set title text color here
        border.setTitleFont(new Font("SansSerif", Font.BOLD, 17));
        testCaseContainer.setBorder(border);


        stepsPanel = new JPanel();
        stepsPanel.setOpaque(false);
        stepsPanel.setLayout(new BoxLayout(stepsPanel, BoxLayout.Y_AXIS));

        actionDropdowns = new ArrayList<>();
        inputComponents = new ArrayList<>();

        JScrollPane stepsScroll = new JScrollPane(stepsPanel);
        stepsScroll.setOpaque(false);
        stepsScroll.getViewport().setOpaque(false);
        testCaseContainer.add(stepsScroll, BorderLayout.CENTER);
    }

    public JPanel getTestCaseContainer() {
        return testCaseContainer;
    }

    public JPanel getStepsPanel() {
        return stepsPanel;
    }

    public List<JComboBox<String>> getActionDropdowns() {
        return actionDropdowns;
    }

    public List<JComponent[]> getInputComponents() {
        return inputComponents;
    }

    public void addActionDropdown(JComboBox<String> actionComboBox) {
        actionDropdowns.add(actionComboBox);
    }

    public void addInputComponents(JComponent[] components) {
        inputComponents.add(components);
    }

    public String getTestCaseName() {
        return testCaseName;
    }
}