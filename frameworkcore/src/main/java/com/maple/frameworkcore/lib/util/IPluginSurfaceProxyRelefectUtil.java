package com.maple.frameworkcore.lib.util;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 插件动态回调工具类
 *
 * @see com.maple.frameworkcore.lib.inter.IPluginSurfaceProxy
 * Created by yuanweinan on 16-7-5.
 */
public class IPluginSurfaceProxyRelefectUtil {

    /**
     * 返回真正需要的view
     *
     * @param target
     */
    public static View getView(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("target为空");
        }

        try {

            // 遍历类里所有方法
            Method[] methods = target.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Log.e("mmmm", methods[i].toString());
            }


            Method onOpenViewMethod = target.getClass().getMethod("getView");
            onOpenViewMethod.setAccessible(true);
            View view = (View) onOpenViewMethod.invoke(target);
            return view;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通知按键点击
     *
     * @return
     */
    public static boolean informKeyEvent(Object target, KeyEvent keyEvent) {
        if (target == null) {
            throw new IllegalArgumentException("target为空");
        }

        try {
            Method onOpenViewMethod = target.getClass().getMethod("informKeyEvent", KeyEvent.class);
            onOpenViewMethod.setAccessible(true);
            return (boolean) onOpenViewMethod.invoke(target, keyEvent);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 通知界面刷新
     *
     * @return
     */
    public static void informRefreshSurface(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("target为空");
        }

        try {
            Method onOpenViewMethod = target.getClass().getMethod("informRefreshSurface");
            onOpenViewMethod.setAccessible(true);
            onOpenViewMethod.invoke(target);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通知销毁
     */
    public static void informDestroy(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("target 为空");
        }
        try {
            Method onOpenViewMethod = target.getClass().getMethod("informDestroy");
            onOpenViewMethod.setAccessible(true);
            onOpenViewMethod.invoke(target);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
