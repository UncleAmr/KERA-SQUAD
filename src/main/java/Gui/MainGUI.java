package Gui;

import javax.swing.*;
import testng.TestRunner;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import translator.ActionTranslator;
import executor.CodeExecutor;
import testng.TestClassGenerator;
import testng.TestNGXmlGenerator;

public class MainGUI {
    private static final String[] actions = {"Open", "Click", "Type", "Scroll","Sleep", "Validate"};
    private static final String[] locatorTypes = {"ID", "Name", "Class", "Type", "Value" , "Placeholder"};

    private final List<TestCasePanel> testCasePanels = new ArrayList<>();
    private boolean isDarkMode = false;
    private JPanel testCasesHolder;
    private JComboBox<String> testCaseSelector;

    public MainGUI(String testCaseName) {
        JFrame frame = new JFrame("KERA Squad Automation Testing Framework - " + testCaseName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 20, 20));

        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                if (isDarkMode) {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(40, 40, 40), 0, getHeight(), new Color(60, 60, 60)));
                } else {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(200, 220, 255)));
                }
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel titleLabel = new JLabel("KERA SQUAD");
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(350, 20, 300, 30);

        testCasesHolder = new JPanel();
        testCasesHolder.setOpaque(false);
        testCasesHolder.setLayout(new BoxLayout(testCasesHolder, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(testCasesHolder);
        scrollPane.setBounds(50, 70, 800, 250);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JButton addStepButton = createStyledButton("Add Step", new Color(144, 224, 239), new Color(0, 180, 216));
        addStepButton.setBounds(50, 340, 180, 40);

        JButton executeAllButton = createStyledButton("Execute", new Color(0, 180, 216), new Color(0, 119, 182));
        executeAllButton.setBounds(260, 340, 180, 40);

        JButton addTestCaseButton = createStyledButton("Add Test Case", new Color(144, 224, 239), new Color(0, 180, 216));
        addTestCaseButton.setBounds(470, 340, 180, 40);

        testCaseSelector = new JComboBox<>();
        testCaseSelector.setBounds(670, 340, 180, 40);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        outputArea.setForeground(Color.DARK_GRAY);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBounds(50, 400, 800, 150);

        JButton themeToggleButton = new JButton(isDarkMode ? "☀" : "☾") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bgStart = isDarkMode ? new Color(80, 80, 80) : new Color(190, 210, 255);
                Color bgEnd = isDarkMode ? new Color(50, 50, 50) : new Color(140, 180, 255);
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
        themeToggleButton.setBounds(780, 20, 30, 30);
        themeToggleButton.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 1));
        themeToggleButton.setContentAreaFilled(false);
        themeToggleButton.setFocusPainted(false);
        themeToggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themeToggleButton.setToolTipText("Toggle theme");

        themeToggleButton.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            themeToggleButton.setText(isDarkMode ? "☀" : "☾");
            DarkModeManager.applyTheme(mainPanel, isDarkMode);
            mainPanel.repaint();
        });

        TestCasePanel firstPanel = createNewTestCase(testCaseName);
        testCaseSelector.addItem(testCaseName);
        


        addStepButton.addActionListener(e -> {
            int selectedIndex = testCaseSelector.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < testCasePanels.size()) {
                addStepToTestCase(testCasePanels.get(selectedIndex));
            }
        });

        addTestCaseButton.addActionListener(e -> {
            String testName = JOptionPane.showInputDialog(null, "Enter test case name:", "New Test Case", JOptionPane.PLAIN_MESSAGE);

            if (testName != null && !testName.trim().isEmpty()) {
                testName = testName.trim();

                for (int i = 0; i < testCaseSelector.getItemCount(); i++) {
                    if (testCaseSelector.getItemAt(i).equalsIgnoreCase(testName)) {
                        JOptionPane.showMessageDialog(null, "A test case with this name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                TestCasePanel panel = createNewTestCase(testName);
                testCaseSelector.addItem(testName);
                testCaseSelector.setSelectedItem(testName);
                testCasesHolder.revalidate();
                testCasesHolder.repaint();
            }
        });



        executeAllButton.addActionListener(e -> {
            StringBuilder allResults = new StringBuilder();

            for (TestCasePanel panel : testCasePanels) {
                List<String> classNames = new ArrayList<>();
                List<String> inputs = new ArrayList<>();

                for (int i = 0; i < panel.getActionDropdowns().size(); i++) {
                    String action = (String) panel.getActionDropdowns().get(i).getSelectedItem();
                    String className = ActionTranslator.translate(action);
                    JComponent[] comps = panel.getInputComponents().get(i);
                    String input;

                    if ("Type".equals(action)) {
                        String locatorType = ((JComboBox<?>) comps[0]).getSelectedItem().toString().toLowerCase();
                        String locatorValue = ((JTextField) comps[1]).getText().trim();
                        String textToType = ((JTextField) comps[2]).getText().trim();
                        input = locatorType + "::" + locatorValue + "::" + textToType;
                    } else if ("Click".equals(action)) {
                        String locatorType = ((JComboBox<?>) comps[0]).getSelectedItem().toString().toLowerCase();
                        String locatorValue = ((JTextField) comps[1]).getText().trim();
                        input = locatorType + "::" + locatorValue;
                    } else if ("Scroll".equals(action)) {
                        input = ((JTextField) comps[1]).getText().trim(); // expects "down::300"
                    }
                    else {
                        input = ((JTextField) comps[1]).getText().trim();
                    }

                    classNames.add(className);
                    inputs.add(input);
                }

                
                String currentTestCaseName = panel.getTestCaseName();
                TestClassGenerator.generateTestClass(currentTestCaseName, classNames, inputs);
                TestNGXmlGenerator.generateXmlByPackage();
//                TestRunner.runTests();
                String result = CodeExecutor.executeSteps(classNames, inputs);
                allResults.append(result).append("\n");
            }

            outputArea.setText(allResults.toString());
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
        closeButton.setBounds(820, 20, 25, 25);
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> System.exit(0));

        mainPanel.add(titleLabel);
        mainPanel.add(scrollPane);
        mainPanel.add(addStepButton);
        mainPanel.add(executeAllButton);
        mainPanel.add(addTestCaseButton);
        mainPanel.add(testCaseSelector);
        mainPanel.add(outputScrollPane);
        mainPanel.add(closeButton);
        mainPanel.add(themeToggleButton);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addStepToTestCase(firstPanel); 
    }

    private TestCasePanel createNewTestCase(String name) {
        TestCasePanel panel = new TestCasePanel(name);
        testCasePanels.add(panel);
        testCasesHolder.add(panel.getTestCaseContainer());
        
        return panel;
    }

    private void addStepToTestCase(TestCasePanel testCasePanel) {
        JPanel stepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stepPanel.setOpaque(false);

        JComboBox<String> actionComboBox = new JComboBox<>(actions);
        JComboBox<String> locatorTypeComboBox = new JComboBox<>(locatorTypes);
        locatorTypeComboBox.setVisible(false);

        JTextField inputField1 = new JTextField(12);
        JTextField inputField2 = new JTextField(12);
        inputField1.setVisible(false);
        inputField2.setVisible(false);

        JButton removeButton = createStyledButton("Remove", new Color(255, 99, 99), new Color(255, 71, 71));

        actionComboBox.addActionListener(ae -> {
            String selected = (String) actionComboBox.getSelectedItem();

            if ("Type".equals(selected) || "Click".equals(selected)) {
                locatorTypeComboBox.setVisible(true);
                inputField1.setVisible(true);
                inputField1.setToolTipText("Enter locator value");
                inputField2.setVisible("Type".equals(selected));
                inputField2.setToolTipText("Text to type");
            } else if ("Validate".equals(selected)) {
                locatorTypeComboBox.setVisible(false);
                inputField1.setVisible(true);
                inputField1.setToolTipText("Expected text");
                inputField2.setVisible(false);
            } else {
                locatorTypeComboBox.setVisible(false);
                inputField1.setVisible(true);
                inputField1.setToolTipText(null);
                inputField2.setVisible(false);
            }

            stepPanel.revalidate();
            stepPanel.repaint();
        });

        removeButton.addActionListener(event -> {
            testCasePanel.getStepsPanel().remove(stepPanel);
            testCasePanel.getActionDropdowns().remove(actionComboBox);
            testCasePanel.getInputComponents().removeIf(comp -> comp[0] == locatorTypeComboBox);
            testCasePanel.getStepsPanel().revalidate();
            testCasePanel.getStepsPanel().repaint();
        });

        stepPanel.add(actionComboBox);
        stepPanel.add(locatorTypeComboBox);
        stepPanel.add(inputField1);
        stepPanel.add(inputField2);
        stepPanel.add(removeButton);

        testCasePanel.getStepsPanel().add(stepPanel);
        testCasePanel.getStepsPanel().revalidate();
        testCasePanel.getStepsPanel().repaint();

        testCasePanel.addActionDropdown(actionComboBox);
        testCasePanel.addInputComponents(new JComponent[]{locatorTypeComboBox, inputField1, inputField2});
    }

    private JButton createStyledButton(String text, Color c1, Color c2) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        return button;
    }

    public static void main(String[] args) {
        new LandingPage();
    }
}