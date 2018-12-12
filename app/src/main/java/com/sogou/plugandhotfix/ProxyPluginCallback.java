package com.sogou.plugandhotfix;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by linchen on 18-12-12.
 * mail: linchen@sogou-inc.com
 *
 */
public interface ProxyPluginCallback {
    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onStop();

    void attachBaseContext(Context hostActivity);

    void onDestroy();
}