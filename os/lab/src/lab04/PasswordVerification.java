package lab04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lab04.validator.DigitsValidator;
import lab04.validator.LengthValidator;
import lab04.validator.LowerCaseValidator;
import lab04.validator.PasswordValidator;
import lab04.validator.SpaceValidator;
import lab04.validator.SpecialCharacterValidator;
import lab04.validator.SpecialNumberValidator;
import lab04.validator.UpperCaseValidator;

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
        final List<String> passwords = readPasswords();
        final PasswordVerification passwordVerification = new PasswordVerification();
        for (int i = 0; i < passwords.size(); i++) passwordVerification.validate(passwords.get(i));
    }
    
    public void validate(final String password) {
        boolean wasSecure = true;
        boolean isSecure;
        
        final PasswordValidator[] validators = new PasswordValidator[] {
                new LengthValidator(), new SpaceValidator(), new DigitsValidator(), new UpperCaseValidator(),
                new LowerCaseValidator(), new SpecialCharacterValidator(), new SpecialNumberValidator()
        };
        
        System.out.print("The password ※" + password + "§");
        
        for (int i = 0; i < RULES.length; i++) {
            isSecure = validators[i].validate(password);
            if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
            if (!isSecure) {
                System.out.println(RULES[i]);
                wasSecure = false;
            }
        }
        
        if (wasSecure) System.out.println("\nCongratulations!  Your password ※" + password + "§ is very secure!");
        System.out.println();
    }
    
    
    private static List<String> readPasswords() {
        final List<String> passwords = new ArrayList<>();
        final Scanner scanner;
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
