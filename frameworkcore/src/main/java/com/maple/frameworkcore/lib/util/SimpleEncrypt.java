package com.maple.frameworkcore.lib.util;
/**
 * 简单转换类
 * Created by yuanweinan on 16-7-14.
 */
public class SimpleEncrypt {

    public static String simpleEncryption(String packageName) {
        /*if (packageName == null) {
            return packageName;
        }
        packageName = packageName.replace(".", "a");
        byte[] b = packageName.getBytes();
        int length = b.length;
        for (int i = 0; i < length; i++) {
            b[i] = (byte) (b[i] ^ 2);
        }
        packageName = StringUtils.byteArrayToString(b);
        LogUtils.i("maple", "转换后名字:" + packageName);*/
        if (packageName == null) {
            return "" + packageName;
        }
        LogUtil.i("maple", packageName + " 转换后名字:" + packageName.hashCode());
        return packageName.hashCode() + "";
    }
}
