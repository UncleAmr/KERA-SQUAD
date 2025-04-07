package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickAction implements InputAction {
    private String elementId;

    @Override
    public void setInput(String input) {
        this.elementId = input;
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        WebElement element = driver.findElement(By.id(elementId));
        element.click();
    }
}
