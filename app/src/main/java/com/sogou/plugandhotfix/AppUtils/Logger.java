package com.sogou.plugandhotfix.AppUtils;

import android.util.Log;

/**
 * Created by linchen on 2018/12/14.
 * mail: linchen@sogou-inc.com
 */
public class Logger {

    public static final String TAG = "Logger";


    public static void println(String info) {
        Log.e(TAG, info);
    }
}
