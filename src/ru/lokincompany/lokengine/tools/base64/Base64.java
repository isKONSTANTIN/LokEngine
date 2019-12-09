package ru.lokincompany.lokengine.tools.base64;

public class Base64 {
    public static String toBase64(String text) {
        return java.util.Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static String fromBase64(String base64) {
        return new String(java.util.Base64.getDecoder().decode(base64));
    }

    public static String bytesToBase64(byte[] text) {
        return java.util.Base64.getEncoder().encodeToString(text);
    }

    public static byte[] bytesFromBase64(String base64) {
        return java.util.Base64.getDecoder().decode(base64);
    }
}