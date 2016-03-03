package lab04.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialCharacterRule extends Rule {

    public SpecialCharacterRule() {
        super("At least 1 special character ¨C The password must contain at least one special character, which can be one of the following 7 choices: ¡®$¡¯, ¡®#¡¯, ¡®@¡¯, ¡®&¡¯, ¡®*¡¯, ¡®?¡¯, or ¡®!¡¯.");
    }

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
