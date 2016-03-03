package lab04.rule;


public class UpperCaseRule extends Rule {

    public UpperCaseRule() {
        super("At least 2 upper-case letters ¨C The password must contain at least two upper-case letter.");
    }

    @Override
    public boolean validate(final String password) {
        return checkUppercase(password);
    }

    private boolean checkUppercase(final String password) {
        return countUppercases(password) >= 2;
    }
    
    private int countUppercases(final String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            count += (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') ? 1 : 0;
        }
        return count;
    }
}
