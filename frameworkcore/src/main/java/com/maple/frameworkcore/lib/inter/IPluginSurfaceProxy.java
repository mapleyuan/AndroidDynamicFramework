package com.maple.frameworkcore.lib.inter;

import android.view.KeyEvent;
import android.view.View;

/**
 * 插件界面代理view(被动)
 * Created by yuanweinan on 16-7-8.
 */
public interface IPluginSurfaceProxy {

    /**
     * 返回真正需要的view
     *
     * @return
     */
    public View getView();

    /**
     * 通知按键点击
     * @param keyEvent
     * @return
     */
    public boolean informKeyEvent(KeyEvent keyEvent);

    /**
     * 通知界面刷新
     */
    public void informRefreshSurface();


    /**
     * 通知销毁
     */
    public void informDestroy();
}
