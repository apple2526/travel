package cn.michael.travel.util;

import java.security.MessageDigest;

public final class Md5Util {
    private Md5Util() {
    }

    public static String encodeByMd5(String password) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] byteArray = md5.digest(password.getBytes());
        return byteArrayToHexString(byteArray);
    }

    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            String hex = byteToHexString(b);
            sb.append(hex);
        }
        return sb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hex[d1] + hex[d2];
    }

    private static final String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}