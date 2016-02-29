package lab04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordVerification {
    
    private static final String[] RULES = new String[] {
        "Rule 1: Valid Length 每 The length of the password must be 8 to 16 only. ",
        "Rule 2: No Space 每 The password must not contain any space or blank character.",
        "Rule 3: At least 2 digits 每 The password must contain at least 2 digits. ",
        "Rule 4: At least 2 upper-case letters 每 The password must contain at least two upper-case letter.",
        "Rule 5: At least 2 lower-case letter 每 The password must contain at least two lower-case letter.",
        "Rule 6: At least 1 special character 每 The password must contain at least one special character, which can be one of the following 7 choices: ＆$＊, ＆#＊, ＆@＊, ＆&＊, ＆*＊, ＆?＊, or ＆!＊.",
        "Rule 7: No special numbers 每 The password must not contain any of the following 7 numbers: 2017, 2016, 2015, 2014, 2013, 2012, or 2011.",
    };
    
    public static void main(String[] args) {
        List<String> passwords = readPasswords();
        PasswordVerification passwordVerification = new PasswordVerification();
        for (int i = 0; i < passwords.size(); i++)
            passwordVerification.validate(passwords.get(i));
    }
    
    public void validate(String password) {
        boolean wasSecure = true;
        boolean isSecure;
        
        System.out.print("The password ※" + password + "§");
        
        isSecure = checkLength(password);
        if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
        if (!isSecure) {
            wasSecure = false;
            System.out.println(RULES[0]);
        }
        
        isSecure = checkSpace(password);
        if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
        if (!isSecure) {
            wasSecure = false;
            System.out.println(RULES[1]);
        }
        
        isSecure = checkDigits(password);
        if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
        if (!isSecure) {
            wasSecure = false;
            System.out.println(RULES[2]);
        }
        
        isSecure = checkUppercase(password);
        if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
        if (!isSecure) {
            wasSecure = false;
            System.out.println(RULES[3]);
        }
        
        isSecure = checklowercase(password);
        if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
        if (!isSecure) {
            wasSecure = false;
            System.out.println(RULES[4]);
        }
        
        isSecure = checkSpecialCharacters(password);
        if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
        if (!isSecure) {
            wasSecure = false;
            System.out.println(RULES[5]);
        }
        
        isSecure = checkSpecialNumbers(password);
        if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
        if (!isSecure) {
            wasSecure = false;
            System.out.println(RULES[6]);
        }

        if (wasSecure)
            System.out.println("\nCongratulations!  Your password ※" + password + "§ is very secure!");
        System.out.println();
    }
    
    private boolean checkLength(String password) {
        return password.length() >= 8 && password.length() <= 16;
    }
    
    private boolean checkSpace(String password) {
        return !password.contains(" ");
    }
    
    private boolean checkDigits(String password) {
        return digitCount(password) >= 2;
    }
    
    private int digitCount(String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            count += (password.charAt(i) >= '0' && password.charAt(i) <= '9')
                    ? 1 : 0;
        }
        return count;
    }
    
    private boolean checkUppercase(String password) {
        return uppercaseCount(password) >= 2;
    }
    
    private int uppercaseCount(String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            count += (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                    ? 1 : 0;
        }
        return count;
    }
    
    private boolean checklowercase(String password) {
        return lowercaseCount(password) >= 2;
    }
    
    private int lowercaseCount(String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            count += (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                    ? 1 : 0;
        }
        return count;
    }
    
    private boolean checkSpecialNumbers(String password) {
        final String[] SPECIAL_NUMBERS = new String[] {
                "2017", "2016", "2015", "2014", "2013", "2012", "2011"
        };
        
        for (int i = 0; i < SPECIAL_NUMBERS.length; i++) if (password.contains(SPECIAL_NUMBERS[i])) return false;
        return true;
    }
    
    private boolean checkSpecialCharacters(String password) {
        String regex = "[$#@&*?!]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    
    private static List<String> readPasswords() {
        List<String> passwords = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new File("files/lab04_passwords"));
            while (scanner.hasNext()) passwords.add(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return passwords;
    }
}
