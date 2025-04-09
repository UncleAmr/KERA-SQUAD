package translator;

public class ActionTranslator {
    public static String translate(String action) {
        switch (action.toLowerCase()) {
            case "open":
                return "actions.OpenAction";
            case "click":
                return "actions.ClickAction";
            case "type":
                return "actions.TypeAction";
            case "sleep":
                return "actions.SleepAction";
            case "validate":
                return "actions.ValidateAction"; 
            default:
                return null;
        }
    }
}
