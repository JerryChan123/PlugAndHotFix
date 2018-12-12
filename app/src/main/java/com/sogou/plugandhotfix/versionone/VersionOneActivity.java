package com.sogou.plugandhotfix.versionone;

import com.sogou.plugandhotfix.AppUtils.AppUtils;
import com.sogou.plugandhotfix.OwnerClassLoader;
import com.sogou.plugandhotfix.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实现文章来源：https://segmentfault.com/a/1190000004062952
 * <p>
 * 该类示例简单加载dex以及apk文件的方式，主要使用DexClassLoader类进行加载，ClassLoader加载外部的Dex或Apk文件，可以加载一些本地APP不存在的类。
 * 加载的方式可以通过反射或者接口的方式来进行，然而使用该方式会造成的问题如下：
 * - 无法使用res目录下的资源，特别是使用XML布局，以及无法通过res资源到达自适应
 * - 无法动态加载新的Activity等组件，因为这些组件需要在Manifest中注册，动态加载无法更改当前APK的Manifest
 * <p>
 */
public class VersionOneActivity extends AppCompatActivity implements View.OnClickListener {
    private String mJarPath = "";
    private static final String TAG = "DexAndApkActivity";

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.load_dex) {
            //加载dex
            loadPlugin("versionone.dex", "PluginTest");
        } else if (id == R.id.load_apk) {
            //加载apk
            loadPlugin("versionone.apk", "com.sogou.plugandhotfix.versionone.PluginTest");
        }
    }

    /**
     * Dex文件
     * <p>
     * <p>
     * public class PluginTest{
     * public String getText(){
     * return "Hello from PluginTest";
     * }
     * }
     */
    private void loadPlugin(String pluginame, String className) {
        mJarPath = AppUtils.copyToDir(this, pluginame);
        OwnerClassLoader classLoader = OwnerClassLoader.newInstance(this, mJarPath, getClassLoader());
        String text = "";
        try {
            Class clazz = classLoader.loadClass(className);
            Method[] methods = clazz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Log.e(TAG, methods[i].toString());
            }
            Method getText = clazz.getDeclaredMethod("getText");
            //调用方法
            text = (String) getText.invoke(clazz.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(VersionOneActivity.this, "获取失败", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(VersionOneActivity.this, "成功获取方法getText结果：" + text, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex_and_apk);
        findViewById(R.id.load_dex).setOnClickListener(this);
        findViewById(R.id.load_apk).setOnClickListener(this);
    }


}
