package com.maple.frameworkcore.lib.pojo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.util.Log;

import com.maple.frameworkcore.lib.core.DynamicloadContextWrapper;
import com.maple.frameworkcore.lib.core.DynamicloadDexReflect;
import com.maple.frameworkcore.lib.inter.IPluginParamsProxy;
import com.maple.frameworkcore.lib.util.DynamicloadReflectHelper;

import java.io.File;

/**
 * Created by yuanweinan on 16-6-27.
 */
public class DynamicloadPlugin {

    /**
     * 包名
     */
    public String mPackageName;

    /**
     * 版本号
     */
    public int mVersionCode;
    /**
     * 类加载器
     */
    private ClassLoader mClassLoader;

    /**
     * 资源
     */
    public Resources mResource;

    /**
     * 插件入口
     */
    public Object mMainEntrance;

    public DynamicloadContextWrapper mContext;


    public DynamicloadPlugin(Context context, File file, IPluginParamsProxy pluginParamsProxy) {
        if (null == pluginParamsProxy) {
            throw new IllegalArgumentException("IPluginParamsProxy can not be null");
        }
        PackageInfo packageInfo = DynamicloadDexReflect.getDexPackageInfo(context, file.getAbsolutePath());
        if (packageInfo != null) {
            mPackageName = packageInfo.packageName;
            mVersionCode = packageInfo.versionCode;
        }
        mClassLoader = DynamicloadDexReflect.createDexClassLoader(context, file.getAbsolutePath());
        mResource = DynamicloadDexReflect.createResource(context, file.getAbsolutePath());
        mContext = new DynamicloadContextWrapper(mPackageName, context, mClassLoader, mResource);
        mMainEntrance = DynamicloadReflectHelper.getMainEntrance(mContext, pluginParamsProxy);
    }

}
