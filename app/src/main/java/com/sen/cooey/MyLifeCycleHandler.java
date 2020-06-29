package com.sen.cooey;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;


public class MyLifeCycleHandler implements ActivityLifecycleCallbacks {

    private final String TAG = MyLifeCycleHandler.this.getClass().getSimpleName();

    private static int resumed = 0;
    private static int paused = 0;
    private static int started = 0;
    private static int stopped = 0;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activity.overridePendingTransition(R.anim.activity_enter_from_right, R.anim.activity_exit_to_left);
        Log.i(TAG, "onActivityCreated " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i(TAG, "onActivityDestroyed - "
                + activity.getClass().getSimpleName());
    }


    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        Log.i(TAG, "onActivityResumed - " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activity.overridePendingTransition(R.anim.activity_enter_from_left, R.anim.activity_exit_to_right);
        ++paused;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i(TAG, "onActivitySaveInstanceState - "
                + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }


    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        Log.w(TAG, "onActivityStopped : application is visible: "
                + (started > stopped));
    }

    // use to check if the application is in foreground/background:
    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForegroundOne() {
        return (resumed) > paused;
    }


    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }


}