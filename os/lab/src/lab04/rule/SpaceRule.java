package lab04.rule;


public class SpaceRule extends Rule {

    public SpaceRule() {
        super("No Space ¨C The password must not contain any space or blank character.");
    }

    @Override
    public boolean validate(final String password) {
        return checkLength(password);
    }

    private boolean checkLength(final String password) {
        return password.length() >= 8 && password.length() <= 16;
    }
}
