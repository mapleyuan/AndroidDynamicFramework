package com.maple.frameworkcore.lib.inter;

import android.view.KeyEvent;
import android.view.View;

import com.maple.frameworkcore.lib.util.IPluginSurfaceProxyRelefectUtil;


/**
 * Created by yuanweinan on 16-7-8.
 */
public class PluginSurfaceProxy implements IPluginSurfaceProxy {

    private Object mPluginSurfaceProxy;


    public PluginSurfaceProxy(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object 为 null");
        }
        mPluginSurfaceProxy = object;
    }

    @Override
    public View getView() {
        return IPluginSurfaceProxyRelefectUtil.getView(mPluginSurfaceProxy);
    }

    @Override
    public boolean informKeyEvent(KeyEvent keyEvent) {
        return IPluginSurfaceProxyRelefectUtil.informKeyEvent(mPluginSurfaceProxy, keyEvent);
    }

    @Override
    public void informRefreshSurface() {
        IPluginSurfaceProxyRelefectUtil.informRefreshSurface(mPluginSurfaceProxy);
    }

    @Override
    public void informDestroy() {
        IPluginSurfaceProxyRelefectUtil.informDestroy(mPluginSurfaceProxy);
    }
}
