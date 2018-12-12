package com.sogou.plugandhotfix;

import android.content.Context;

import dalvik.system.DexClassLoader;

/**
 * Created by linchen on 18-12-12.
 * mail: linchen@sogou-inc.com
 */
public class OwnerClassLoader extends DexClassLoader {
    public static OwnerClassLoader newInstance(Context context, String dexPath, ClassLoader parent) {
        OwnerClassLoader classLoader = new OwnerClassLoader(dexPath, context.getDir("dex", 0).getAbsolutePath(), null, parent);
        return classLoader;
    }

    private OwnerClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
    }
}
