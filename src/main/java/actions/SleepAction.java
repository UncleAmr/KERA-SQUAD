package actions;

import org.openqa.selenium.WebDriver;

public class SleepAction implements InputAction {
    private int seconds;

    @Override
    public void setInput(String input) {
        try {
            this.seconds = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            this.seconds = 1;
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        Thread.sleep(seconds * 1000L);
    }
}
