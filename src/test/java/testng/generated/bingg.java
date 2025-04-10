package testng.generated;

import org.testng.annotations.Test;
import executor.CodeExecutor;
import java.util.List;

public class bingg {
    @Test
    public void run() {
        CodeExecutor.executeSteps(
            List.of("actions.OpenAction", "actions.SleepAction"),
            List.of("https://x.com/", "5")
        );
    }
}
