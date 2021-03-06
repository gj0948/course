package lab04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lab04.rule.DigitsRule;
import lab04.rule.LengthRule;
import lab04.rule.LowerCaseRule;
import lab04.rule.Rule;
import lab04.rule.SpaceRule;
import lab04.rule.SpecialCharacterRule;
import lab04.rule.SpecialNumberRule;
import lab04.rule.UpperCaseRule;

public class PasswordVerification {
    
    private static final Rule[] passwordRules = new Rule[] {
            new LengthRule(),
            new SpaceRule(),
            new DigitsRule(),
            new UpperCaseRule(),
            new LowerCaseRule(),
            new SpecialCharacterRule(),
            new SpecialNumberRule(),
    };
    
    private static List<String> readPasswords() {
        final List<String> passwords = new ArrayList<>();
        try {
            final Scanner scanner = new Scanner(new File("files/lab04_passwords"));
            while (scanner.hasNext()) passwords.add(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return passwords;
    }
    
    public void validate(final String password) {
        boolean wasSecure = true;
        boolean isSecure;
        
        System.out.print("The password ��" + password + "��");
        
        for (int i = 0; i < passwordRules.length; i++) {
            isSecure = passwordRules[i].validate(password);
            if (!isSecure && wasSecure) System.out.println(" violates the following rule:");
            if (!isSecure) {
                System.out.println(passwordRules[i].getErrorMessage());
                wasSecure = false;
            }
        }
        
        if (wasSecure) System.out.println("\nCongratulations!  Your password ��" + password + "�� is very secure!");
        System.out.println();
    }
    
    public static void main(String[] args) {
        final List<String> passwords = readPasswords();
        final PasswordVerification passwordVerification = new PasswordVerification();
        for (int i = 0; i < passwords.size(); i++) passwordVerification.validate(passwords.get(i));
    }
    
}
