package com.example.frontend.utils;

public class PasswordEncoder {

    public static String hashPassword(String password) {
        char[] chars = password.toCharArray();
        long hash = 0;
        for (char c : chars) {
            hash = (hash * 31 + c) % 1_000_000_007;
        }
        return Long.toHexString(hash);
    }
}
