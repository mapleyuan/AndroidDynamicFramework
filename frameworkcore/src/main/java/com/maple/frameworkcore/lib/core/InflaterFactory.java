package com.maple.frameworkcore.lib.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by wubinqi on 16-8-11.
 */
public class InflaterFactory implements LayoutInflater.Factory {

    private LayoutInflater.Factory mBaseFactory;
    private ClassLoader mClassLoader;

    public InflaterFactory(LayoutInflater.Factory base, ClassLoader classLoader) {
        if (null == classLoader) {
            throw new IllegalArgumentException("classLoader is null");
        }
        mBaseFactory = base;
        mClassLoader = classLoader;
    }

    @Override
    public View onCreateView(String s, Context context, AttributeSet attributeSet) {
        View v = null;
        try {
            if (s != null && s.contains(".")) {
                Class<?> clazz = mClassLoader.loadClass(s);
                Constructor c = clazz.getConstructor(Context.class, AttributeSet.class);
                v = (View) c.newInstance(context, attributeSet);
            }
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        } catch (InvocationTargetException e) {
        }
        return v != null ? v : (mBaseFactory != null ? mBaseFactory.onCreateView(s, context, attributeSet) : null);
    }
}
