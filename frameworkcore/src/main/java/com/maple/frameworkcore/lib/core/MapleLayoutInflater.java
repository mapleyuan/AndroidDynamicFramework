package com.maple.frameworkcore.lib.core;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maple.frameworkcore.lib.util.LogUtil;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by yuanweinan on 16-7-14.
 */
public class MapleLayoutInflater extends LayoutInflater {

    private LayoutInflater mParent;
    private LayoutInflater mNow;

    protected MapleLayoutInflater(LayoutInflater original, LayoutInflater now, Context newContext) {
        super(now, newContext);
        mParent = original;
        mNow = now;
    }

    @Override
    public View inflate(@LayoutRes int resource, @Nullable ViewGroup root) {
        try {

            return mNow.inflate(resource, root);
        } catch (Exception e) {
            LogUtil.i("maple", "插件找不到该resouce:" + resource);
            return mParent.inflate(resource, root);
        }
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root) {
        try {
            return mNow.inflate(parser, root);
        } catch (Exception e) {
            return mParent.inflate(parser, root);
        }
    }

    @Override
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        try {
            return mNow.inflate(resource, root, attachToRoot);
        } catch (Exception e) {
            return mParent.inflate(resource, root, attachToRoot);
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return mNow.cloneInContext(newContext);
    }
}
