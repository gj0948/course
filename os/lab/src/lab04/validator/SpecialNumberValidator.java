package lab04.validator;


public class SpecialNumberValidator implements PasswordValidator {

    @Override
    public boolean validate(final String password) {
        return checkSpecialNumbers(password);
    }

    private boolean checkSpecialNumbers(final String password) {
        final String[] SPECIAL_NUMBERS = new String[] {
                "2017", "2016", "2015", "2014", "2013", "2012", "2011"
        };
        
        //TODO: use regex to find the pattern.
        
        for (int i = 0; i < SPECIAL_NUMBERS.length; i++) if (password.contains(SPECIAL_NUMBERS[i])) return false;
        return true;
    }
}
