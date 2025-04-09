package actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenAction implements InputAction {
    private String url;

    @Override
    public void setInput(String input) {
        this.url = input;
    }

    @Override
    public void run(WebDriver driver) {
    	
        // This method won't use the passed driver — it'll create and return a new one
        if (url == null || url.isEmpty()) {
            System.out.println("No URL provided.");
            return;
        }

        System.setProperty("webdriver.chrome.driver", "C:/Users/AMR/chromedriver-win64/chromedriver.exe"); // Your path
        driver.get(url);
    }

    // New helper method
    public WebDriver createDriver() {
        System.setErr(new java.io.PrintStream(new java.io.OutputStream() {
            public void write(int b) {}
        }));
        System.setProperty("webdriver.chrome.driver", "C:/Users/AMR/chromedriver-win64/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
