package com.zone.plugin.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zone.plugin.MyApplication;
import com.zone.plugin.activity.TargetActivity;

import java.lang.reflect.Field;

public class ActivityThreadHandlerCallback implements Handler.Callback {
    private static final String TAG = "HCallback";

    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG, "[handleMessage] msg = " + msg.what);
        switch (msg.what) {
            case 100:
                handleLaunchActivity(msg);
                break;
        }
        // 保证mH中的handleMessage可以继续执行
        return false;
    }

    private void handleLaunchActivity(Message msg) {
        Log.d(TAG, "[handleLaunchActivity]");
        Object obj = msg.obj;
        try {
            //拦截处理LAUNCH_ACTIVITY消息，替换Intent中的目标Activity信息
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            raw.setClass(MyApplication.getGlobalContext(), TargetActivity.class);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
