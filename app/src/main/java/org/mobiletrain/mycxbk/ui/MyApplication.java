package org.mobiletrain.mycxbk.ui;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by 天一 on 2016/9/29.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
    }
}
