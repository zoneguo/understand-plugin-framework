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

        switch (msg.what) {
            case 100:
                handleLaunchActivity(msg);
                break;
        }

        return false;
    }

    private void handleLaunchActivity(Message msg) {
        Log.d(TAG, "[handleLaunchActivity]");
        Object obj = msg.obj;
        try {
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
