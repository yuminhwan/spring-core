package com.example.springboot;

public class PasswordMeter {
    private static boolean meetLength(final String pw) {
        return pw.length() >= 8;
    }

    public PasswordStrength meter(final String pw) {
        if (pw == null || pw.isBlank()) {
            throw new IllegalArgumentException();
        }
        int metCount = calculateMetCount(pw);
        if (metCount == 1 || metCount == 0) {
            return PasswordStrength.WEAK;
        }
        if (metCount == 2) {
            return PasswordStrength.NORMAL;
        }
        return PasswordStrength.STRONG;
    }

    private int calculateMetCount(final String pw) {
        int metCount = 0;
        if (meetLength(pw)) {
            metCount++;
        }
        if (containsUppercase(pw)) {
            metCount++;
        }
        if (containsDigit(pw)) {
            metCount++;
        }
        return metCount;
    }

    private boolean containsUppercase(final String pw) {
        for (char word : pw.toCharArray()) {
            if ('A' <= word && word <= 'Z') {
                return true;
            }
        }
        return false;
    }

    private boolean containsDigit(final String pw) {
        for (char word : pw.toCharArray()) {
            if ('0' <= word && word <= '9') {
                return true;
            }
        }
        return false;
    }
}
