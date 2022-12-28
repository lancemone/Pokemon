package com.timothy.common.arouter;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

public class ARouterManager {

    public static void init(Application application, boolean printLog){
        if (printLog){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    public static void toTraditionColorActivity(Context context){
        ARouter.getInstance().build(ARouterPath.PATH_TRADITION_COLOR_ACTIVITY).navigation(context);
    }
}
