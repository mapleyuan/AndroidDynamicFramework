package com.maple.frameworkcore.lib.util;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.maple.frameworkcore.lib.core.DynamicloadContextWrapper;
import com.maple.frameworkcore.lib.inter.IFrameworkCenterCallBack;
import com.maple.frameworkcore.lib.inter.IPluginParamsProxy;

import java.lang.reflect.Method;

/**
 * Created by yuanweinan on 16-6-27.
 */
public class DynamicloadReflectHelper {

    private final static String PLUGIN_ENTRANCE_CLASS = "com.maple.frameworkcore.lib.MainEntrance";

    /**
     * 获取插件入口
     *
     * @param context
     * @param pluginParamsProxy
     * @return
     */
    public static Object getMainEntrance(DynamicloadContextWrapper context, IPluginParamsProxy pluginParamsProxy) {
        try {
            Class libProviderClazz = context.getClassLoader().loadClass(PLUGIN_ENTRANCE_CLASS);
            Object mainEntrance = libProviderClazz.getDeclaredConstructor(Context.class, Object.class).newInstance(context, pluginParamsProxy);
            return mainEntrance;
        } catch (Exception exception) {
            LogUtil.d("maple", "无法获取插件主入口MainEntrance:" + exception.getMessage());
            return null;
        }
    }

    /**
     * 返回插件主界面view
     *
     * @return
     */
    public static Object getPluginFullProxy(Object mainEntrance, IFrameworkCenterCallBack frameworkCenterCallBack) {
        try {
            Method getPluginDetailView = mainEntrance.getClass().getMethod("getPluginMainView", Object.class);
            Object mainView = getPluginDetailView.invoke(mainEntrance, frameworkCenterCallBack);
            return mainView;
        } catch (Exception exception) {
            LogUtil.d("maple", "无法获取插件getPluginMainView得到主view:" + exception.getMessage());
            return null;
        }
    }


    /**
     * 通知点击键盘
     *
     * @param real
     * @param keyCode
     * @param event
     */
    public static boolean informKeyDown(DynamicloadContextWrapper context, Object real, int keyCode, KeyEvent event) {
        try {
            Class libProviderClazz = context.getClassLoader().loadClass(real.getClass().getName());
            Method getPluginDetailView = libProviderClazz.getMethod("onKeyDown", int.class, KeyEvent.class);
            return (Boolean) getPluginDetailView.invoke(real, keyCode, event);
        } catch (Exception exception) {
            // Handle exception gracefully here.
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * 返回所代理的view
     *
     * @param real
     * @return
     */
    public static View getRealView(DynamicloadContextWrapper context, Object real) {
        try {
            Class libProviderClazz = context.getClassLoader().loadClass(real.getClass().getName());
            Method getPluginDetailView = libProviderClazz.getMethod("getView");
            return (View) getPluginDetailView.invoke(real);
        } catch (Exception exception) {
            // Handle exception gracefully here.
            exception.printStackTrace();
            return null;
        }
    }
}
