package com.timothy.common.manager;

import android.annotation.SuppressLint;
import android.app.Application;

import com.timothy.common.lifecycle.CommonActivityLifecycleCallback;

import java.lang.reflect.InvocationTargetException;

public class AppManager{

    private volatile static AppManager instance;
    private Application mApplication;
    private CommonActivityLifecycleCallback mActivityLifecycleCallback;


    public static AppManager getInstance(){
        if (instance == null){
            synchronized (AppManager.class){
                instance = new AppManager();
            }
        }
        return instance;
    }

    private AppManager(){}

    public void register(Application application){
        mApplication = application;
        mActivityLifecycleCallback = CommonActivityLifecycleCallback.getInstance();
        mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallback);
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleCallback);
    }

    public Application getApplication(){
        if (mApplication == null){
            mApplication = getApplicationByReflect();
        }
        return mApplication;
    }

    public boolean isAppForeground(){
        return true;
    }

    public String myPackageName(){
        return mApplication.getPackageName();
    }

    private Application getApplicationByReflect(){
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Class<?> currentActivityThread = (Class<?>) activityThread.getMethod("currentActivityThread").invoke(null);
            Application app = (Application) activityThread.getMethod("getApplication").invoke(currentActivityThread);
            return app;
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }
}
