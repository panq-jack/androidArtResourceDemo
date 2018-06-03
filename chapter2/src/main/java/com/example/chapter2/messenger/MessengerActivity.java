package com.example.chapter2.messenger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.chapter2.R;
import com.example.mylibrary.utils.MyConstants;

public class MessengerActivity extends Activity {

    private static final String TAG = "ppp_MessengerActivity";

    private Messenger mService;
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
    private static int increNum=0;

    @SuppressLint("handlerLeak")
    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MyConstants.MSG_FROM_SERVICE:
                Log.d(TAG, "receive msg from Service:" + msg.getData().getString("reply"));

                sendMessageToServer();
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            Log.d(TAG, "bind service");
//            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
//            Bundle data = new Bundle();
//            data.putString("msg", "hello, this is client.");
//            msg.setData(data);
//            msg.replyTo = mGetReplyMessenger;
//            try {
//                mService.send(msg);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
            sendMessageToServer();
        }

        public void onServiceDisconnected(ComponentName className) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        // service intent must  be explicit
//        Intent intent = new Intent("com.example.chapter2.MessengerService.launch");
        Intent intent = new Intent(MessengerActivity.this,MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    /**
     * 发送一个消息
     */
    private void sendMessageToServer(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
                Bundle data = new Bundle();
                data.putString("msg", "hello, this is client.  no  . "+(++increNum));
                msg.setData(data);
                msg.replyTo = mGetReplyMessenger;
                try {
                    Thread.sleep(1000);
                    mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
