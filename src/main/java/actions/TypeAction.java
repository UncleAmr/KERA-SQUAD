package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypeAction implements InputAction {
    private String locator;
    private String text;
    @Override
    public void setInput(String input) {
        String[] parts = input.split("::", 3);  
        String locatorType = parts.length > 0 ? parts[0] : "";
        String locatorValue = parts.length > 1 ? parts[1] : "";
        this.text = parts.length > 2 ? parts[2] : "";

        switch (locatorType.toLowerCase()) {
            case "name":
                this.locator = "name::" + locatorValue;
                break;
            case "id":
                this.locator = "id::" + locatorValue;
                break;
            case "placeholder":
                this.locator = "placeholder::" + locatorValue;
                break;
            case "class":
                this.locator = "class::" + locatorValue;
                break;
            default:
                this.locator = "unknown::" + locatorValue;
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        String[] locatorParts = locator.split("::", 2);
        String type = locatorParts[0];
        String value = locatorParts[1];

        WebElement inputBox = null;

        switch (type) {
            case "name":
                inputBox = driver.findElement(By.name(value));
                break;
            case "id":
                inputBox = driver.findElement(By.id(value));
                break;
            case "placeholder":
                inputBox = driver.findElement(By.xpath("//*[contains(@placeholder,'" + value + "')]"));
                break;
            case "class":
                inputBox = driver.findElement(By.className(value));
                break;
            default:
                throw new Exception("Unknown locator type: " + type);
        }

        if (inputBox != null) {
            inputBox.sendKeys(text);
        } else {
            throw new Exception("No input field found using " + type + ": " + value);
        }
    }

}
