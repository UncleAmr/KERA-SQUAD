package actions;

import org.openqa.selenium.WebDriver;

public class ValidateAction implements InputAction {
    private String expectedText;

    @Override
    public void setInput(String input) {
        this.expectedText = input;
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        if (driver.getPageSource().contains(expectedText)) {
            System.out.println("Validation passed: Found '" + expectedText + "'");
        } else {
        	 System.out.println("Validation Failed : Expected text not found");
        }
    }
}
