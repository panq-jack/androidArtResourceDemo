package com.example.chapter13;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.example.chapter13.crashtest.CrashHandler;

public class TestApp extends Application {

    private static TestApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        MultiDex.install(this);

    }

    public static TestApp getInstance() {
        return sInstance;
    }

}
