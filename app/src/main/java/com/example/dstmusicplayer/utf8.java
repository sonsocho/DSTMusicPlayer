package com.example.dstmusicplayer;

import android.util.Base64;


public class utf8 {

    public static String encodeString(String songPath) {
        return Base64.encodeToString(songPath.getBytes(), Base64.DEFAULT);
    }
    public static String decodeString(String songPath) {
        return new String(Base64.decode(songPath, Base64.DEFAULT));
    }
}
