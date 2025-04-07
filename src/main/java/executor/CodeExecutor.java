package executor;

import actions.InputAction;
import actions.OpenAction;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class CodeExecutor {

    public static String executeSteps(List<String> classNames, List<String> inputs) {
        StringBuilder result = new StringBuilder();
        WebDriver driver = null;

        try {
            for (int i = 0; i < classNames.size(); i++) {
                String className = classNames.get(i);
                String input = inputs.get(i);

                Class<?> clazz = Class.forName(className);
                InputAction action = (InputAction) clazz.getDeclaredConstructor().newInstance();
                action.setInput(input);

                if (action instanceof OpenAction) {
                    OpenAction openAction = (OpenAction) action;
                    driver = openAction.createDriver();
                    openAction.run(driver);
                } else if (driver != null) {
                    action.run(driver);
                } else {
                    result.append("No browser open to run step ").append(i + 1).append("\n");
                }

                result.append("Step ").append(i + 1).append(": Executed ").append(className).append("\n");
            }
        } catch (Exception e) {
            result.append("Execution failed: ").append(e.getMessage()).append("\n");
        } finally {
            if (driver != null) {
//                driver.quit();
            }
        }

        return result.toString();
    }
}
