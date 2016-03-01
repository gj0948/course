package lab04.validator;


public class SpaceValidator implements PasswordValidator {

    @Override
    public boolean validate(final String password) {
        return checkLength(password);
    }

    private boolean checkLength(final String password) {
        return password.length() >= 8 && password.length() <= 16;
    }
}
