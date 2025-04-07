package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypeAction implements InputAction {
    private String locator;
    private String text;

    @Override
    public void setInput(String input) {
        String[] parts = input.split("::", 2);
        this.locator = parts.length > 0 ? parts[0] : "";
        this.text = parts.length > 1 ? parts[1] : "";
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        
        WebElement inputBox = null;
        try {
            inputBox = driver.findElement(By.name(locator));
        } catch (Exception ignored) {}
        if (inputBox == null) {
            try {
                inputBox = driver.findElement(By.id(locator));
            } catch (Exception ignored) {}
        }
        if (inputBox == null) {
            try {
           
                inputBox = driver.findElement(By.xpath("//*[contains(@placeholder,'" + locator + "')]"));
            } catch (Exception ignored) {}
        }

        if (inputBox != null) {
            inputBox.sendKeys(text);
        } else {
            throw new Exception("No input field found with locator: " + locator);
        }
    }
}
