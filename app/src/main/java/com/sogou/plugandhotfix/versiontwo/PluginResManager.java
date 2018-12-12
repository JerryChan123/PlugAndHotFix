package com.sogou.plugandhotfix.versiontwo;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by linchen on 18-12-12.
 * mail: linchen@sogou-inc.com
 */
public class PluginResManager {

    private AssetManager mAssetManager;
    private static PluginResManager sPluginResManager;
    private Resources mResources;

    public static PluginResManager getInstance() {
        if (sPluginResManager == null) {
            sPluginResManager = new PluginResManager();
        }
        return sPluginResManager;
    }

    private PluginResManager() {

    }

    public AssetManager getAssetManager() {
        return mAssetManager;
    }

    public Resources getResource() {
        return mResources;
    }

    public void initPluginResManager(Context context, String dexPath) {
        AssetManager assetManager = context.getAssets();
        Method addAssetPath = null;
        try {
            addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            mAssetManager = assetManager;
            mResources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
