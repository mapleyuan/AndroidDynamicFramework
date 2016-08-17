package com.maple.frameworkcore.lib.inter;

/**
 * 插件初始化参数代理,主动
 * Created by yuanweinan on 16-7-8.
 */
public interface IPluginParamsProxy {

    /**
     * 返回广告id
     * @return
     */
    public String getADID(String packageName);


    /**
     * 买量渠道号
     * @return
     */
    public String getBuychannel(String packageName);


    /**
     * 安装天数
     * @return
     */
    public String getInstallDays(String packageName);
}
