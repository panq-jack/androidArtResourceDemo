package com.example.chapter1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.File;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            String test = savedInstanceState.getString("extra_test");
            Log.d(TAG, "[onCreate]restore extra_test:" + test);
        }
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent("com.ryg.charpter_1.c");
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, SecondActivity.class);
                intent.putExtra("time", System.currentTimeMillis());
                intent.addCategory("com.ryg.category.c");
//                intent.setDataAndType(Uri.parse("http://abc"), "text/plain");
                intent.setDataAndType(getFileUrl(MainActivity.this,new File(getExternalCacheDir()+File.separator+"file.jpg"),intent),"text/plain");
//                intent.setDataAndType(getPictureUri(MainActivity.this, new File("file://abc"), intent),"text/plain");
                startActivity(intent);
            }
        });
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent, time=" + intent.getLongExtra("time", 0));
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged, newOrientation:" + newConfig.orientation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putString("extra_test", "test");
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Log.d(TAG, "onRestoreInstanceState");
        String test = savedInstanceState.getString("extra_test");
        Log.d(TAG, "[onRestoreInstanceState]restore extra_test:" + test);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }



    /**
     * 兼容Android N及以上  6.0
     * 生成拍照所需的uri
     *
     * @param file 文件对象
     * @return uri
     */

    private static Uri getFileUrl(Context context, File file, Intent intent) {
        Uri uri;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", file);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 给目标应用一个临时授权
            }
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
