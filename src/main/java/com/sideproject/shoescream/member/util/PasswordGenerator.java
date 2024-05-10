package com.sideproject.shoescream.member.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*";
    private static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;

    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder(length);

        passwordBuilder.append(getRandomChar(UPPER_CASE, random));
        passwordBuilder.append(getRandomChar(LOWER_CASE, random));
        passwordBuilder.append(getRandomChar(DIGITS, random));
        passwordBuilder.append(getRandomChar(SPECIAL_CHARACTERS, random));

        for (int i = 4; i < length; i++) {
            passwordBuilder.append(getRandomChar(ALL_CHARACTERS, random));
        }

        List<Character> passwordCharacters = new ArrayList<>();
        for (char c : passwordBuilder.toString().toCharArray()) {
            passwordCharacters.add(c);
        }
        Collections.shuffle(passwordCharacters);

        StringBuilder finalPassword = new StringBuilder(length);
        for (char c : passwordCharacters) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }

    private static char getRandomChar(String characters, SecureRandom random) {
        return characters.charAt(random.nextInt(characters.length()));
    }
}
