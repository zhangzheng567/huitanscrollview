package com.example.zhangzheng.myapplication1;

import android.app.Application;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HeConfig.init("HE1809301527011979","dd09c66493c94973a33532fc31d7265f");
        HeConfig.switchToFreeServerNode();
    }
}
