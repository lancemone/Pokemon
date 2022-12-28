package com.timothy.common.manager;

import android.app.Application;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;

public class MMKVManager {

    private static final String MMKV_APPLICATION_ONCE_ID = "APPLICATION_ONCE_ID";

    public static void initMMKV(@NonNull Application application){
        MMKV.initialize(application);
    }

    public static MMKV ofDefault(){return MMKV.defaultMMKV();}

    public static MMKV ofApplicationOnce(){return MMKV.mmkvWithID(MMKV_APPLICATION_ONCE_ID);}
}
