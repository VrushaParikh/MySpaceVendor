package com.example.myspace2.utils;

import android.content.Context;
import android.widget.Toast;

public class Config {

    public static int user_id = -1;

    //    public static String url = "http://192.168.0.104/";
    public static String url = "http://192.168.43.61/myspace/api/";
//    public static String imageUrl = url + "storage/";

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
