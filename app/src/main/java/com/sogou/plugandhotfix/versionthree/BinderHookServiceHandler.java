package com.sogou.plugandhotfix.versionthree;

import com.sogou.plugandhotfix.AppUtils.Logger;

import android.content.ClipData;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by linchen on 2018/12/14.
 * mail: linchen@sogou-inc.com
 */
public class BinderHookServiceHandler implements InvocationHandler {

    private Object mOriginalService;

    public BinderHookServiceHandler(IBinder binder, Class clazz) {
        try {

            Method asInterface = clazz.getDeclaredMethod("asInterface", IBinder.class);
            //原始对象
            mOriginalService = asInterface.invoke(null, binder);
        } catch (Exception e) {

        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 把剪切版的内容替换为 "you are hooked"
        if ("getPrimaryClip".equals(method.getName())) {
            Logger.println("hook getPrimaryClip");
            return ClipData.newPlainText(null, "you are hooked");
        }

        // 欺骗系统,使之认为剪切版上一直有内容
        if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }

        return method.invoke(mOriginalService, args);
    }
}
