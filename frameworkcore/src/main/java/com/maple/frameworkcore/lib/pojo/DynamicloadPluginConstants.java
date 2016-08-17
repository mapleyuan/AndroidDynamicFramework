package com.maple.frameworkcore.lib.pojo;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import com.maple.frameworkcore.lib.util.SimpleEncrypt;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanweinan on 16-6-27.
 */
public class DynamicloadPluginConstants {

    public static final int VERSION_CODE = 1; // 插件中心版本号

    private static String sCOMMON_FILE_PATH = null;

    public static final String DLP_PREFFIX = "com.maple.plugins";

    public static String getYourHostDexPath(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context 不能为空");
        }

        if (sCOMMON_FILE_PATH == null) {
            String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//            List<String> sdList = getAllSDPaths(context);
//            int index = sdList.indexOf(externalPath);
//            if (index >= 0) { // 外置sd卡优先
                sCOMMON_FILE_PATH = externalPath + "/Android/frameworkcore";
//            } else if (sdList.size() > 0) {
//                sCOMMON_FILE_PATH = sdList.get(0) + "/Android/frameworkcore";
//            } else {
//                sCOMMON_FILE_PATH = externalPath + "/Android/frameworkcore";
//            }
        }

        String packageName = context.getPackageName();
        packageName = SimpleEncrypt.simpleEncryption(packageName);
        String path = sCOMMON_FILE_PATH + "/" + packageName;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static List<String> getAllSDPaths(Context context) {
        List<String> files = new ArrayList<String>();
        StorageManager sm = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            String[] paths = (String[]) getVolumePathsMethod.invoke(sm, params);

            for (int i = 0; i < paths.length; i++) {
                String status = (String) sm.getClass()
                        .getMethod("getVolumeState", String.class)
                        .invoke(sm, paths[i]);
                if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
                    files.add(paths[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return files;
        }
        return files;
    }


}
