package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickAction implements InputAction {
    private String locatorType;
    private String locatorValue;

    @Override
    public void setInput(String input) {
        // Expecting format: type::value (e.g., id::login-button)
        String[] parts = input.split("::");
        if (parts.length == 2) {
            locatorType = parts[0].toLowerCase();
            locatorValue = parts[1];
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        By by;
        switch (locatorType) {
            case "id":
                by = By.id(locatorValue);
                break;
            case "name":
//                by = By.name(locatorValue);
            	by = By.xpath("//*[contains(@name, '" + locatorValue + "')]");
                
                break;
            case "class":
                by = By.className(locatorValue);
                break;
            case "type":
                by = By.cssSelector("[type='" + locatorValue + "']");
                break;
            case "value":
                by = By.cssSelector("[value='" + locatorValue + "']");
                break;
            default:
                throw new Exception("Unsupported locator type: " + locatorType);
        }
        WebElement element = driver.findElement(by);
        element.click();
    }
}
