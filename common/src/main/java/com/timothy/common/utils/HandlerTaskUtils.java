package com.timothy.common.utils;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.NonNull;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.utils.HandlerTaskUtils
 * @Author: MoTao
 * @Date: 2023-03-10
 * <p>
 * <p/>
 */
public class HandlerTaskUtils {

    private static final String DEFAULT_HANDLER_THREAD_NAME = "default_pokemon_handler_thread";
    private static volatile HandlerTaskUtils handlerTaskUtils;

    private final Handler mainHandler;
    private final Handler defaultThreadHandler;
    private HandlerTaskUtils(){
        HandlerThread handlerThread = new HandlerThread(DEFAULT_HANDLER_THREAD_NAME);
        handlerThread.start();
        mainHandler = new Handler(Looper.getMainLooper());
        defaultThreadHandler = new Handler(handlerThread.getLooper());
    }

    public void postOnMain(@NonNull Runnable runnable){
        mainHandler.post(runnable);
    }

    public void postDelayedOnMain(@NonNull Runnable runnable, long delayMillis){
        mainHandler.postDelayed(runnable, delayMillis);
    }

    public void postOnDefaultThread(@NonNull Runnable runnable){
        defaultThreadHandler.post(runnable);
    }

    public void postDelayedOnDefaultThread(@NonNull Runnable runnable, long delayMillis){
        defaultThreadHandler.postDelayed(runnable, delayMillis);
    }

    public Handler getMainHandler() {
        return mainHandler;
    }

    public Handler getDefaultThreadHandler() {
        return defaultThreadHandler;
    }

    public static HandlerTaskUtils getInstance(){
        if (handlerTaskUtils == null){
            synchronized (HandlerTaskUtils.class){
                if (handlerTaskUtils == null){
                    handlerTaskUtils = new HandlerTaskUtils();
                }
            }
        }
        return handlerTaskUtils;
    }
}
