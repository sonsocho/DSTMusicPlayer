package com.example.dstmusicplayer;

import android.util.Base64;


public class utf8 {

    public static String encodeString(String songPath) {
        String path = removeSpaces(songPath);
        return Base64.encodeToString(path.getBytes(), Base64.DEFAULT);
    }

    public static String decodeString(String songPath) {
        String path = removeSpaces(songPath);
        return new String(Base64.decode(path, Base64.DEFAULT));
    }

    // Hàm xóa dấu cách
    public static String removeSpaces(String input) {
        if (input == null) {
            return null; // Trả về null nếu đầu vào là null
        }
        return input.replaceAll("\\s+", ""); // Xóa tất cả các dấu cách
    }
}

