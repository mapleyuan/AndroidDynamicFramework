package com.maple.frameworkcore.lib.inter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 插件详情页代理
 * Created by yuanweinan on 16-7-5.
 */
public class DynamicloadDelegateView extends RelativeLayout implements IPluginSurfaceProxy {

    public DynamicloadDelegateView(Context context, IPluginSurfaceProxy viewProxy) {
        super(context);
        mViewProxy = viewProxy;
        addView(mViewProxy.getView());
    }
    private IPluginSurfaceProxy mViewProxy;

    @Override
    public View getView() {
        return mViewProxy.getView();
    }

    @Override
    public boolean informKeyEvent(KeyEvent keyEvent) {
        return mViewProxy.informKeyEvent(keyEvent);
    }

    @Override
    public void informRefreshSurface() {
        mViewProxy.informRefreshSurface();
    }

    @Override
    public void informDestroy() {
        mViewProxy.informDestroy();
    }
}
