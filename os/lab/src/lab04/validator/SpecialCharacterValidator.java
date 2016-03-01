package lab04.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialCharacterValidator implements PasswordValidator {

    @Override
    public boolean validate(final String password) {
        return checkSpecialCharacters(password);
    }

    private boolean checkSpecialCharacters(final String password) {
        final Pattern pattern = Pattern.compile("[$#@&*?!]");
        final Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
