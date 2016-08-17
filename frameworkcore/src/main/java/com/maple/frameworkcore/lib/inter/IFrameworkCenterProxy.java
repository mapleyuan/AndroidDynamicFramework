package com.maple.frameworkcore.lib.inter;

import android.view.View;

/**
 * 插件框架代理(被动)
 * Created by yuanweinan on 16-7-8.
 */
public interface IFrameworkCenterProxy {

    /**
     * 返回真正需要的view
     *
     * @return
     */
    public abstract View getView();

}
