package lab04.validator;

public abstract class Rule implements PasswordValidator {
    private static final String RULE = "Rule ";
    private static final String COLON = ": ";
    
    private static int ruleCount = 0;
    
    private final int ruleId;
    private final String errorMessage;
    
    public Rule(final String errorMessage) {
        this.ruleId = ++ruleCount;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return RULE + ruleId + COLON + errorMessage;
    }
}
