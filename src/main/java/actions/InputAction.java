package actions;

import org.openqa.selenium.WebDriver;

public interface InputAction {
    void setInput(String input);
    void run(WebDriver driver) throws Exception;
}
