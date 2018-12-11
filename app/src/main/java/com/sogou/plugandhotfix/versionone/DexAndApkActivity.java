package com.sogou.plugandhotfix.versionone;

import com.sogou.plugandhotfix.R;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 实现文章来源：https://segmentfault.com/a/1190000004062952
 * <p>
 * 该类示例简单加载dex以及apk文件的方式，主要使用DexClassLoader类进行加载，ClassLoader加载外部的Dex或Apk文件，可以加载一些本地APP不存在的类。
 * 使用该方式会造成的问题如下：
 * - 很难使用插件APK里的res资源，这意味着无法使用新的XML布局等资源，无法更改Manifest清单文件，所以无法启动新的Activity等组件
 */
public class DexAndApkActivity extends AppCompatActivity {
    private String mJarPath = "";
    private static final String TAG = "DexAndApkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex_and_apk);
        findViewById(R.id.load_dex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
                OwnerClassLoader classLoader = new OwnerClassLoader(mJarPath, getDir("dex", 0).getAbsolutePath(), null, getClassLoader());
                String text = "";
                try {
                    Class clazz = classLoader.loadClass("PluginTest");
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
                    Toast.makeText(DexAndApkActivity.this, "获取失败" , Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(DexAndApkActivity.this, "成功获取方法getText结果：" + text, Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void copyToDir(File file) {
        try {
            String name = "pluginsimple.dex";
            InputStream stream = getAssets().open(name);
            byte[] readBytes = new byte[1024];
            File saveFile = new File(file.getAbsolutePath() + "/" + name);
            mJarPath = saveFile.getAbsolutePath();
            if (saveFile.exists()) {
                saveFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(saveFile);
            int i=0;
            while ((i=stream.read(readBytes)) >0) {
                outputStream.write(readBytes,0,i);
            }
            stream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class OwnerClassLoader extends DexClassLoader {

        public OwnerClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
            super(dexPath, optimizedDirectory, librarySearchPath, parent);
        }
    }
}
