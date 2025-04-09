package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestCasePanel {
    private final JPanel testCaseContainer;
    private final JPanel stepsPanel;
    private final List<JComboBox<String>> actionDropdowns;
    private final List<JComponent[]> inputComponents;

    public TestCasePanel(String testCaseTitle) {
        testCaseContainer = new JPanel();
        testCaseContainer.setLayout(new BorderLayout());
        testCaseContainer.setOpaque(false);
        testCaseContainer.setBorder(BorderFactory.createTitledBorder(testCaseTitle));

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
}
