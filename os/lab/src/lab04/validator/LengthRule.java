package lab04.validator;


public class LengthRule extends Rule {

    public LengthRule() {
        super("Valid Length ¨C The length of the password must be 8 to 16 only.");
    }

    @Override
    public boolean validate(final String password) {
        return checkLength(password);
    }

    private boolean checkLength(final String password) {
        return password.length() >= 8 && password.length() <= 16;
    }
}
