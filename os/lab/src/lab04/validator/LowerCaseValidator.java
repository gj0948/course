package lab04.validator;


public class LowerCaseValidator implements PasswordValidator {

    @Override
    public boolean validate(final String password) {
        return checklowercase(password);
    }

    private boolean checklowercase(final String password) {
        return countLowercases(password) >= 2;
    }
    
    private int countLowercases(final String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            count += (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') ? 1 : 0;
        }
        return count;
    }
}
