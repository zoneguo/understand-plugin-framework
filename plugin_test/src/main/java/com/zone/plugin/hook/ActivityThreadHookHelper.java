package com.zone.plugin.hook;

import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;

public class ActivityThreadHookHelper {
    // 用反射的方式设置ActivityThread中mH对象mCallback成员为我们自己定义的Callback
    public static void hookActivityThreadHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(currentActivityThread);

            Field mCallBackField = Handler.class.getDeclaredField("mCallback");
            mCallBackField.setAccessible(true);
            mCallBackField.set(mH, new ActivityThreadHandlerCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
