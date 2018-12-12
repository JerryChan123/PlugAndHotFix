package com.sogou.plugandhotfix.versiontwo;

import com.sogou.plugandhotfix.AppUtils.AppUtils;
import com.sogou.plugandhotfix.OwnerClassLoader;
import com.sogou.plugandhotfix.ProxyPluginCallback;
import com.sogou.plugandhotfix.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class ProxyActivity extends Activity {
    private ProxyPluginCallback mPluginCallback;
    private OwnerClassLoader mClassLoader;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        initPlugin();
        if (mPluginCallback != null) {
            mPluginCallback.attachBaseContext(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        if (mPluginCallback != null) {
            mPluginCallback.onCreate(savedInstanceState);
        }
    }


    private void initPlugin() {
        mClassLoader = OwnerClassLoader.newInstance(this, copyPlugin(), getClassLoader());
        try {
            Class aClass = mClassLoader.loadClass("com.lin.pluginapk.MainActivity");
            mPluginCallback = (ProxyPluginCallback) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private String copyPlugin() {
        String path = AppUtils.copyToDir(this, "versiontwo.apk");
        return path;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPluginCallback != null) {
            mPluginCallback.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPluginCallback != null) {
            mPluginCallback.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPluginCallback != null) {
            mPluginCallback.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPluginCallback != null) {
            mPluginCallback.onDestroy();
        }
    }

}
