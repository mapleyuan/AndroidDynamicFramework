package com.maple.frameworkcore.lib;

import android.content.Context;
import android.util.Log;

import com.maple.plugins.plugindemo.MainView;

/**
 * 插件入口模板
 * Created by yuanweinan on 16-6-30.
 */
public final class MainEntrance {
    private Context mContext;
    private Object mPluginParamsProxy;

    /**
     * 构造方法
     *
     * @param context
     */
    public MainEntrance(Context context, Object pluginParamsProxy) {
        mContext = context;
        mPluginParamsProxy = pluginParamsProxy;
        Log.i("hzw", "MainEntrance proxy:" + pluginParamsProxy);
    }


    /**
     * 返回插件主界面
     *
     * @return
     */
    public IPluginSurfaceProxy getPluginMainView(Object frameworkCenterCallBackObject) {
        return new MainView(mContext);
    }

}



