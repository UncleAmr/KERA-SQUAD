package actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ScrollAction implements BaseAction, InputAction {

    private String input; // Expected format: direction::value (e.g., down::500 or up::300)

    @Override
    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public void run(WebDriver driver) {
        try {
            String[] parts = input.split("::");
            String direction = parts[0].trim().toLowerCase();
            int value = Integer.parseInt(parts[1].trim());

            int yOffset = direction.equals("up") ? -value : value;

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, arguments[0]);", yOffset);

            System.out.println("Scrolled " + direction + " by " + value + "px");
        } catch (Exception e) {
            System.out.println("ScrollAction failed: " + e.getMessage());
        }
    }
}
