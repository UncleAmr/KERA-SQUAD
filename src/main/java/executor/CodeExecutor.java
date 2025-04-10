package executor;

import actions.InputAction;
import actions.OpenAction;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CodeExecutor {
	private static String extractMeaningfulError(String fullMessage) {
	    if (fullMessage == null) return "Unknown error.";

	    
	    if (fullMessage.contains("Unable to locate element")) {
	        int startIndex = fullMessage.indexOf("Unable to locate element");
	        int endIndex = fullMessage.indexOf("}", startIndex); // End after the JSON-like selector
	        if (endIndex != -1) {
	            return fullMessage.substring(startIndex, endIndex + 1)
	                    .replace("\"method\":\"", "")
	                    .replace("\"selector\":\"", "")
	                    .replace("{", "")
	                    .replace("}", "")
	                    .replace("\"", "")
	                    .replace("method:", "")
	                    .replace("selector:", "")
	                    .replace(",", " ")
	                    .trim();
	        } else {
	            return fullMessage.substring(startIndex).split("\n")[0]; 
	        }
	    }
	    return fullMessage.split("\n")[0];
	}

    public static String executeSteps(List<String> classNames, List<String> inputs) {
        StringBuilder result = new StringBuilder();
        WebDriver driver = null;

        for (int i = 0; i < classNames.size(); i++) {
            String className = classNames.get(i);
            String input = inputs.get(i);
            try {
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
                    result.append("[Step ").append(i + 1).append("] ❌ No browser session open.\n");
                    continue;
                }

                result.append("[Step ").append(i + 1).append("] ✅ Executed ").append(getActionName(className)).append("\n");

            } catch (Exception e) {
                String simplifiedMessage = extractMeaningfulError(e.getMessage());
                result.append("[Step ").append(i + 1).append("] ❌ Error in action ")
                      .append(getActionName(className)).append(": ")
                      .append(simplifiedMessage).append("\n");
            }

        }

        if (driver != null) {
            driver.quit();
        }

        return result.toString();
    }

    private static String getActionName(String className) {
        if (className == null || className.isEmpty()) return "Unknown Action";
        return className.substring(className.lastIndexOf('.') + 1).replace("Action", "");
    }
}
