package com.sogou.plugandhotfix.versionthree;

import com.sogou.plugandhotfix.R;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 第三种方式启动
 * DroidPlugin的文章分析：https://blog.csdn.net/zhangyawen1i/article/details/52000689
 */
public class VersionThreeActivity extends AppCompatActivity implements View.OnClickListener {
    private Object mActThread;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.start_act == id) {
            //测试Hook Activity的启动方法
            // http://weishu.me/2016/01/28/understand-plugin-framework-proxy-hook/
            getActivityThread();
            hookInstrumentation(mActThread);
            Intent intent = new Intent(VersionThreeActivity.this, TestActivity.class);
            startActivity(intent);
        } else if (R.id.test_hook_binder == id) {
            //测试Hook ClipboardManager
            // http://weishu.me/2016/02/16/understand-plugin-framework-binder-hook/
            getClipBoardManager();
        }
    }

    private void getClipBoardManager() {
        try {
            //获取ServiceManager
            Class sm = Class.forName("android.os.ServiceManager");
            //获取getService方法
            Method method = sm.getDeclaredMethod("getService", String.class);
            //静态方法，可以直接获得Clipboard对象，一般来说这是一个Binder代理对象
            IBinder clipboard = (IBinder) method.invoke(null, Context.CLIPBOARD_SERVICE);
            IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(sm.getClassLoader(),  new Class<?>[] { IBinder.class }, new BinderProxyHookHandler(clipboard));
            // 把这个hook过的Binder代理对象放进ServiceManager的cache里面
// 以后查询的时候 会优先查询缓存里面的Binder, 这样就会使用被我们修改过的Binder了
            Field cacheField = sm.getDeclaredField("sCache");
            cacheField.setAccessible(true);
            Map<String, IBinder> cache = (Map) cacheField.get(null);
            cache.put(CLIPBOARD_SERVICE, hookedBinder);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_three);
        findViewById(R.id.start_act).setOnClickListener(this);
        findViewById(R.id.test_hook_binder).setOnClickListener(this);
    }


    //获取当前ActivityThread
    private void getActivityThread() {
        try {

            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            mActThread = currentActivityThreadMethod.invoke(null);
        } catch (Exception e) {

        }
    }

    private void hookInstrumentation(Object activityThread) {
        try {
            Field field = activityThread.getClass().getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(activityThread);
            ProxyInstrumentation proxy = new ProxyInstrumentation(instrumentation);
            field.set(activityThread, proxy);
        } catch (Exception e) {

        }
    }
}
