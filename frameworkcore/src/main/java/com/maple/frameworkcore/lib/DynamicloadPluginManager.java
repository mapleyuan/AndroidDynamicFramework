package com.maple.frameworkcore.lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.maple.frameworkcore.lib.core.DynamicloadDexReflect;
import com.maple.frameworkcore.lib.inter.IFrameworkCenterCallBack;
import com.maple.frameworkcore.lib.inter.IPluginParamsProxy;
import com.maple.frameworkcore.lib.inter.IPluginSurfaceProxy;
import com.maple.frameworkcore.lib.inter.PluginSurfaceProxy;
import com.maple.frameworkcore.lib.pojo.DynamicloadPlugin;
import com.maple.frameworkcore.lib.pojo.DynamicloadPluginConstants;
import com.maple.frameworkcore.lib.util.DynamicloadReflectHelper;
import com.maple.frameworkcore.lib.util.LogUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 插件管理类,跟插件无关的其他业务不要放在这个类(如升级,放到DynamicloadManager这个类)
 * Created by yuanweinan on 16-6-27.
 */
public class DynamicloadPluginManager {

    public static DynamicloadPluginManager getInstance(Context context) {
        synchronized (DynamicloadPluginManager.class) {
            if (sInstance == null) {
                synchronized (DynamicloadPluginManager.class) {
                    if (sInstance == null) {
                        sInstance = new DynamicloadPluginManager(context.getApplicationContext());
                    }
                }
            }
        }
        return sInstance;
    }


    /**
     * 获取指定插件
     *
     * @param packageName
     * @return
     */
    public IPluginSurfaceProxy getPlugins(String packageName, IFrameworkCenterCallBack dynamicloadCallBack) {
        return openDex(packageName, dynamicloadCallBack);
    }

    /**
     * 获取指定插件列表
     *
     * @param packageNameList
     * @return
     */
    public List<IPluginSurfaceProxy> getPlugins(List<String> packageNameList, IFrameworkCenterCallBack frameworkCenterCallBack) {
        List<IPluginSurfaceProxy> views = new ArrayList<IPluginSurfaceProxy>();
        for (String packageName : packageNameList) {
            views.add(getPlugins(packageName, frameworkCenterCallBack));
        }
        return views;
    }

    /**
     * * 初始化加载插件资源,假如之前加载过,不再重新加载sdcard资源,假如需要强制刷新,请调用refresh方法@method refreshPlugins
     */
    public void initPreload(IPluginParamsProxy pluginParamsProxy) {
        File[] files = scanPluginPath();
        if (files == null) {
            return;
        }
        mDynamicloadPlugins.clear();
        for (File file : files) {
            DynamicloadPlugin dynamicloadPlugin = mPluginsMap.get(file.getAbsolutePath());
            if (dynamicloadPlugin == null) {
                dynamicloadPlugin = new DynamicloadPlugin(mContext, file, pluginParamsProxy);
                mPluginsMap.put(file.getAbsolutePath(), dynamicloadPlugin);
            }
            if (TextUtils.isEmpty(dynamicloadPlugin.mPackageName)) {
                //过滤掉可能为null的异常
                continue;
            }
            mDynamicloadPlugins.put(dynamicloadPlugin.mPackageName, dynamicloadPlugin);
        }
    }

    /**
     * 强制刷新所有插件
     * 调用该接口注意不要使用原有缓存view
     *
     * @param pluginParamsProxy
     */
    public void refreshPlugins(IPluginParamsProxy pluginParamsProxy) {
        File[] files = scanPluginPath();
        if (files == null) {
            return;
        }
        mDynamicloadPlugins.clear();
        for (File file : files) {
            DynamicloadPlugin dynamicloadPlugin = new DynamicloadPlugin(mContext, file, pluginParamsProxy);
            mPluginsMap.put(file.getAbsolutePath(), dynamicloadPlugin);
            if (TextUtils.isEmpty(dynamicloadPlugin.mPackageName)) {
                //过滤掉可能为null的异常
                continue;
            }
            mDynamicloadPlugins.put(dynamicloadPlugin.mPackageName, dynamicloadPlugin);
        }
    }

    /**
     * 强制刷新指定包名插件
     * 调用该接口注意不要使用原有缓存view
     *
     * @param pluginParamsProxy
     */
    public void refreshPlugins(IPluginParamsProxy pluginParamsProxy, String packageName) {
        LogUtil.i("hzw", "刷新插件：" + packageName);
        File[] files = scanPluginPath();
        if (files == null) {
            return;
        }
        for (File file : files) {
            PackageInfo packageInfo = DynamicloadDexReflect.getDexPackageInfo(mContext, file.getAbsolutePath());
            if (packageName.equals(packageInfo.packageName)) {
                DynamicloadPlugin dynamicloadPlugin = new DynamicloadPlugin(mContext, file, pluginParamsProxy);
                if (TextUtils.isEmpty(dynamicloadPlugin.mPackageName)) {
                    //过滤掉可能为null的异常
                    continue;
                }
                mPluginsMap.put(file.getAbsolutePath(), dynamicloadPlugin);
                mDynamicloadPlugins.put(dynamicloadPlugin.mPackageName, dynamicloadPlugin);
                break;
            }

        }
    }

    /**
     * 该插件是否可用
     *
     * @param packageName
     * @return
     */
    public boolean isPluginAvaible(String packageName) {
        return mDynamicloadPlugins.get(packageName) != null;
    }


    /**
     * 获取插件
     *
     * @param packageName
     * @return
     */
    public DynamicloadPlugin getDynamicloadPlugin(String packageName) {
        return mDynamicloadPlugins.get(packageName);
    }

    private Map<String, DynamicloadPlugin> mDynamicloadPlugins;
    private static DynamicloadPluginManager sInstance;
    private Context mContext;

    //预存插件map
    private HashMap<String, DynamicloadPlugin> mPluginsMap;

    private DynamicloadPluginManager(Context context) {
        mContext = context;
        mDynamicloadPlugins = new HashMap<String, DynamicloadPlugin>();
        mPluginsMap = new HashMap<String, DynamicloadPlugin>();
    }

    /**
     * 打开指定dex
     */
    private IPluginSurfaceProxy openDex(String packageName, IFrameworkCenterCallBack frameworkCenterCallBack) {
        DynamicloadPlugin dynamicloadPlugin = mDynamicloadPlugins.get(packageName);
        if (dynamicloadPlugin == null) {
            return null;
        }
        Object proxyView = DynamicloadReflectHelper.getPluginFullProxy(dynamicloadPlugin.mMainEntrance, frameworkCenterCallBack);
        return new PluginSurfaceProxy(proxyView);
    }


    /**
     * 扫描指定的插件目录
     *
     * @return
     */
    private File[] scanPluginPath() {
        String path = DynamicloadPluginConstants.getYourHostDexPath(mContext);
        File[] vaildFileList = getVaild(path);
        return vaildFileList;
    }

    /**
     * 获取合法文件列表
     *
     * @param path
     * @return
     */
    private File[] getVaild(String path) {
        File files = new File(path);
        String[] strings = files.list();
        File[] vaildList = files.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                try {
                    PackageInfo packageInfo = mContext.getPackageManager()
                            .getPackageArchiveInfo(
                                    pathname.getAbsolutePath(),
                                    PackageManager.GET_ACTIVITIES
                                            | PackageManager.GET_SERVICES);
                    if (packageInfo.packageName.contains(DynamicloadPluginConstants.DLP_PREFFIX)) {
                        return true;
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    return false;
                }
                return false;
            }
        });
        return vaildList;
    }


}
