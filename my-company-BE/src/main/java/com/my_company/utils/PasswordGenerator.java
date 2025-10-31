package com.my_company.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    private final SecureRandom random = new SecureRandom();
    private final int length;
    private final boolean useUppercase;
    private final boolean useLowercase;
    private final boolean useDigits;
    private final boolean useSpecial;

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public PasswordGenerator(int length, boolean useUppercase, boolean useLowercase,
                             boolean useDigits, boolean useSpecial) {
        if (length <= 0) throw new IllegalArgumentException("Password length must be > 0");
        if (!useUppercase && !useLowercase && !useDigits && !useSpecial)
            throw new IllegalArgumentException("At least one character set must be enabled");
        this.length = length;
        this.useUppercase = useUppercase;
        this.useLowercase = useLowercase;
        this.useDigits = useDigits;
        this.useSpecial = useSpecial;
    }

    public String generate() {
        List<Character> passwordChars = new ArrayList<>();

        // Her setten en az bir karakter ekle
        if (useUppercase) passwordChars.add(randomCharFrom(UPPERCASE));
        if (useLowercase) passwordChars.add(randomCharFrom(LOWERCASE));
        if (useDigits) passwordChars.add(randomCharFrom(DIGITS));
        if (useSpecial) passwordChars.add(randomCharFrom(SPECIAL));

        // Kalan uzunluğu rastgele doldur
        StringBuilder charPool = new StringBuilder();
        if (useUppercase) charPool.append(UPPERCASE);
        if (useLowercase) charPool.append(LOWERCASE);
        if (useDigits) charPool.append(DIGITS);
        if (useSpecial) charPool.append(SPECIAL);

        while (passwordChars.size() < length) {
            passwordChars.add(randomCharFrom(charPool.toString()));
        }

        // Shuffle ederek dağılımı rastgele yap
        Collections.shuffle(passwordChars, random);

        // Listeden string oluştur
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    private char randomCharFrom(String chars) {
        return chars.charAt(random.nextInt(chars.length()));
    }
}
