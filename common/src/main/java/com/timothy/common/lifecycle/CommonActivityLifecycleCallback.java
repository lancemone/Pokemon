package com.timothy.common.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.Stack;

public class CommonActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    private volatile static CommonActivityLifecycleCallback instance;

    private CommonActivityLifecycleCallback(){}

    public static CommonActivityLifecycleCallback getInstance(){
        if (instance == null){
            synchronized (CommonActivityLifecycleCallback.class){
                if (instance == null){
                    instance = new CommonActivityLifecycleCallback();
                }
            }
        }
        return instance;
    }

    private final Stack<WeakReference<Activity>> activityStack = new Stack<>();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        activityStack.add(new WeakReference<>(activity));
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activityStack.removeIf(item -> item.get() == activity);
    }
}
