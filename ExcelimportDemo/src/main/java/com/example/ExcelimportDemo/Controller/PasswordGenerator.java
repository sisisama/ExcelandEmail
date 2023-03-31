package com.example.ExcelimportDemo.Controller;
import java.security.SecureRandom;

public class PasswordGenerator {
	private static final String SPECIAL_CHARS = "!@#$%&";
    public static String generateTokenPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        boolean hasSpecialChar = false;

        while (sb.length() < length) {
            char nextChar = (char) random.nextInt(128);

            if (Character.isLetterOrDigit(nextChar)) {
                sb.append(nextChar);
            } else if (!hasSpecialChar && SPECIAL_CHARS.indexOf(nextChar) != -1) {
                sb.append(nextChar);
                hasSpecialChar = true;
            }
        }

        if (!hasSpecialChar) {
            int specialCharIndex = random.nextInt(SPECIAL_CHARS.length());
            sb.setCharAt(random.nextInt(length), SPECIAL_CHARS.charAt(specialCharIndex));
        }

        return sb.toString();
    }

}
