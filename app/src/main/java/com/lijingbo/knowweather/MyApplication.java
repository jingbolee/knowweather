package com.lijingbo.knowweather;

import android.app.Application;

import org.xutils.x;

/**
 * @FileName: com.lijingbo.knowweather.MyApplication.java
 * @Author: Li Jingbo
 * @Date: 2016-03-30 21:10
 * @Version V1.0 <描述当前版本功能>
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }
}
