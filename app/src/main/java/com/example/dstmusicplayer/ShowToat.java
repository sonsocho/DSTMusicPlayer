package com.example.dstmusicplayer;

import android.content.Context;
import android.widget.Toast;

public class ShowToat {
    public void showToa(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
