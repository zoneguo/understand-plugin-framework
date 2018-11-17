package com.zone.plugin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.zone.plugin.hook.ActivityThreadHookHelper;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "[onCreate]");
        sContext = this;
        hook();
    }

    public static Context getGlobalContext() {
        return sContext;
    }

    private void hook() {
        ActivityThreadHookHelper.hookActivityThreadHandler();
    }
}
