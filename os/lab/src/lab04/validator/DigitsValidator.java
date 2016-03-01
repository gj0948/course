package lab04.validator;


public class DigitsValidator implements PasswordValidator {

    @Override
    public boolean validate(final String password) {
        return checkDigits(password);
    }
    
    private boolean checkDigits(final String password) {
        return countDights(password) >= 2;
    }
    
    private int countDights(final String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            count += (password.charAt(i) >= '0' && password.charAt(i) <= '9') ? 1 : 0;
        }
        return count;
    }
}
