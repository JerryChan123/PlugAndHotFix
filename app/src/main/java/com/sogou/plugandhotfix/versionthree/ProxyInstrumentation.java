package com.sogou.plugandhotfix.versionthree;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by linchen on 2018/12/13.
 * mail: linchen@sogou-inc.com
 */
public class ProxyInstrumentation extends Instrumentation {
    private static final String TAG = "PorxyInstrumentation";
    private Instrumentation mInstrumentation;

    public ProxyInstrumentation(Instrumentation instrumentation) {
        mInstrumentation = instrumentation;
    }

    /**
     * Hook Activity的启动方式
     * @param who
     * @param contextThread
     * @param token
     * @param target
     * @param intent
     * @param requestCode
     * @param options
     * @return
     */
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        try {

            Log.e(TAG, "load before");
            Method method = Instrumentation.class.getMethod("execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            method.setAccessible(true);
            ActivityResult result = (ActivityResult) method.invoke(mInstrumentation, who,
                    contextThread, token, target, intent, requestCode, options);
            Log.e(TAG, "load after");
            return result;
        } catch (Exception e) {

        }
        return null;
    }
}
