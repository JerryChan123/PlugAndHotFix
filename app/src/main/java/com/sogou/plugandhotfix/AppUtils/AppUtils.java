package com.sogou.plugandhotfix.AppUtils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by linchen on 18-12-12.
 * mail: linchen@sogou-inc.com
 */
public class AppUtils {
    public static String copyToDir(Context context, String name) {
        String mJarPath = "";
        try {
            InputStream stream = context.getAssets().open(name);
            byte[] readBytes = new byte[1024];
            File father = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            File saveFile = new File(father.getAbsolutePath() + "/" + name);
            mJarPath = saveFile.getAbsolutePath();
            if (saveFile.exists()) {
                saveFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(saveFile);
            int i = 0;
            while ((i = stream.read(readBytes)) > 0) {
                outputStream.write(readBytes, 0, i);
            }
            stream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJarPath;
    }
}
