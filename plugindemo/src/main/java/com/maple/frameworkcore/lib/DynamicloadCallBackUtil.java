package com.maple.frameworkcore.lib;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 插件动态回调工具类
 * Created by yuanweinan on 16-7-5.
 */
public class DynamicloadCallBackUtil {

    // 插件cardItem可见／被遮住的广播，主要用来通知插件cardItem在被遮住时停止动画　by hzw
    public final static String ACTION_PLUGIN_CARD_VIEW_CAN_SEE = "action_plugin_card_view_can_see";
    public final static String ACTION_PLUGIN_CARD_VIEW_BE_COVERED = "action_plugin_card_view_be_covered";


    /**
     * 打开全屏view
     * @param target
     */
    public static String getADID(Object target, String packageName) {
        if (target == null) {
            throw new IllegalArgumentException("target为空");
        }

        try {
            Class dynamicCallBackClass = Class.forName(target.getClass().getName());
            Method onOpenViewMethod = dynamicCallBackClass.getMethod("getADID", String.class);
            onOpenViewMethod.setAccessible(true);
            return (String) onOpenViewMethod.invoke(target, packageName);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 买量渠道号
     * @return
     */
    public static String getBuychannel(Object target, String packageName) {
        if (target == null) {
            throw new IllegalArgumentException("target为空");
        }

        try {
            Class dynamicCallBackClass = Class.forName(target.getClass().getName());
            Method onOpenViewMethod = dynamicCallBackClass.getMethod("getBuychannel", String.class);
            onOpenViewMethod.setAccessible(true);
            return (String) onOpenViewMethod.invoke(target, packageName);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 安装天数
     * @return
     */
    public static String getInstallDays(Object target, String packageName) {
        if (target == null) {
            throw new IllegalArgumentException("target为空");
        }

        try {
            Class dynamicCallBackClass = Class.forName(target.getClass().getName());
            Method onOpenViewMethod = dynamicCallBackClass.getMethod("getInstallDays", String.class);
            onOpenViewMethod.setAccessible(true);
            return (String) onOpenViewMethod.invoke(target, packageName);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 主动推出全屏view
     * @param target
     * @param view
     */
    public static void onBack(Object target, View view) {
        if (target == null || view == null) {
            throw new IllegalArgumentException("target或view 为空");
        }
        try {
            Class dynamicCallBackClass = Class.forName(target.getClass().getName());
            Method onOpenViewMethod = dynamicCallBackClass.getMethod("onBack", View.class);
            onOpenViewMethod.setAccessible(true);
            onOpenViewMethod.invoke(target, view);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主动推出全屏view
     * @param target
     * @param view
     */
    public static void informShowFullScreenView(Object target, View view) {
        if (target == null || view == null) {
            throw new IllegalArgumentException("target或view 为空");
        }
        try {
            Class dynamicCallBackClass = Class.forName(target.getClass().getName());
            Method onOpenViewMethod = dynamicCallBackClass.getMethod("informShowFullScreenView", View.class);
            onOpenViewMethod.setAccessible(true);
            onOpenViewMethod.invoke(target, view);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出Activity
     */
    public static void informExit(Object target, Object obj) {
        if (target == null) {
            throw new IllegalArgumentException("target 为空");
        }
        try {
            Class dynamicCallBackClass = Class.forName(target.getClass().getName());
            Method onOpenViewMethod = dynamicCallBackClass.getMethod("informExit", Object.class);
            onOpenViewMethod.setAccessible(true);
            onOpenViewMethod.invoke(target, obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
