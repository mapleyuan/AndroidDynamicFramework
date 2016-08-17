package com.maple.frameworkcore.lib.inter;

import android.view.View;

/**
 * 打开插件中心回调接口(主动)
 * Created by yuanweinan on 16-7-8.
 */
public interface IFrameworkCenterCallBack {

    /***
     * 插件中心主动退出接口
     */
    public void onBack(View view);

    /**
     * 插件中心想要展示全屏view
     * @return
     */
    public void informShowFullScreenView(View view);

    public void informExit(Object obj);
}
