package com.maple.plugins.plugindemo;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.maple.frameworkcore.lib.IPluginSurfaceProxy;

/**
 * Created by yuanweinan on 16-8-15.
 */
public class MainView extends RelativeLayout implements IPluginSurfaceProxy {

    public MainView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.activity_main, this);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean informKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void informRefreshSurface() {

    }

    @Override
    public void informDestroy() {

    }
}
