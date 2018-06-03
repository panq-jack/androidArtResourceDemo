package com.example.chapter2;



import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.example.chapter2.binderpool.BinderPool;
import com.example.mylibrary.utils.MyUtils;

public class MyApplication extends Application {

    private static final String TAG = "ppp_MyApplication";

    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        String processName = MyUtils.getProcessName(getApplicationContext(),
                Process.myPid());
        Log.d(TAG, "application start, process name:" + processName);
        new Thread(new Runnable() {

            @Override
            public void run() {
                doWorkInBackground();
            }
        }).start();
    }

    private void doWorkInBackground() {
        // init binder pool
        BinderPool.getInsance(getApplicationContext());
    }
}
