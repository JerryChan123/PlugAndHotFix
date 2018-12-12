package com.sogou.plugandhotfix;

import android.app.Activity;
import android.content.res.Resources;
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

    void attachHost(Activity activity, Resources resources);
    void onDestroy();
}