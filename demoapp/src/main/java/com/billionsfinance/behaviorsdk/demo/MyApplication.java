package com.billionsfinance.behaviorsdk.demo;

import android.app.Application;

import com.billionsfinance.behaviorsdk.BehaviorAgent;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //enable log
        BehaviorAgent.setDebugMode(true);
        BehaviorAgent.setCatchUncaughtExceptions(false);
        //init sdk
        BehaviorAgent.initialize(this, "MMT", "b9e024eafbff46419ff351d103ad7c8f", "应用宝");
        //BehaviorAgent.initialize(this, "FQG", "15ac789c029548e7ab09462230496b4d", "应用宝");
        //BehaviorAgent.getInstance().setUserId("userId");
    }
}
