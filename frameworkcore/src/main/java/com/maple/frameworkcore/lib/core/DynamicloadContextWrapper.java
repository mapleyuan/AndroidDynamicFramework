package com.maple.frameworkcore.lib.core;

import android.annotation.TargetApi;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;

import java.lang.reflect.Field;

/**
 * Created by yuanweinan on 16-6-22.
 */
public class DynamicloadContextWrapper extends ContextWrapper {

    private ClassLoader mClassLoader;
    private Resources mResource;
    private AssetManager mAssetManager;
    private Resources.Theme mTheme;
    private String mPackageName;
    private LayoutInflater mLayoutInflater;

    public DynamicloadContextWrapper(String packageName, Context base, ClassLoader classLoader, Resources resources) {
        super(base);
        mClassLoader = classLoader;
        mResource = resources;
        mAssetManager = resources.getAssets();
        mTheme = resources.newTheme();
        mTheme.setTo(base.getTheme());
        mPackageName = packageName;

    }


    @Override
    public Resources getResources() {
//        Log.i("maple", "reName:" + mResource.getString());
//        Log.i("maple", "base:" + LayoutInflater.from(this).getContext().getResources().getResourceName(0x7f020009));
        return mResource;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        getBaseContext().registerComponentCallbacks(callback);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        getBaseContext().unregisterComponentCallbacks(callback);
    }

    @Override
    public Object getSystemService(String name) {
        Object object = super.getSystemService(name);
        if (object instanceof LayoutInflater) {
//            invokeLayout(object);
            if (mLayoutInflater == null) {
//                注意不同android版本兼容
                mLayoutInflater = ((LayoutInflater) object).cloneInContext(this);
                InflaterFactory factory = new InflaterFactory(mLayoutInflater.getFactory(), getClassLoader());
                mLayoutInflater.setFactory(factory);
                //解决toast找不到
                mLayoutInflater = new MapleLayoutInflater((LayoutInflater) object, mLayoutInflater, this);
            }
            return mLayoutInflater;
        }
        return object;
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }

    public void invokeLayout(Object layoutInflater) {
        try {
            Class layout = Class.forName("android.view.LayoutInflater");
            Field[] fields = layout.getDeclaredFields();
            int k = 0;
            for (int i = 0; i < fields.length; i++) {
                Log.e("maple", fields[i].toString());
                if (fields[i].toString().contains("protected final android.content.Context android.view.LayoutInflater.mContext")) {
                    k = i;
                    break;
                }
            }
            fields[k].setAccessible(true);

            fields[k].set(layoutInflater, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager;
    }

    @Override
    public Resources.Theme getTheme() {
        return mTheme;
    }


    @Override
    public String getPackageName() {
        return getBaseContext().getPackageName();
    }

    @Override
    public ClassLoader getClassLoader() {
        ClassLoader classLoader = new ClassLoader(super.getClassLoader()) {
            @Override
            public Class<?> loadClass(String className) throws ClassNotFoundException {
                Class<?> clazz = mClassLoader.loadClass(className);
                Log.d("maple", "initPreload class:" + className);
                if (clazz == null) {
                    clazz = getParent().loadClass(className);
                }
                // still not found
                if (clazz == null) {
                    throw new ClassNotFoundException(className);
                }

                return clazz;
            }
        };
        return classLoader;
//        return mClassLoader;
    }
}
