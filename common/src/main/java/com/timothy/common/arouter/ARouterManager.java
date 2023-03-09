package com.timothy.common.arouter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

public class ARouterManager {

    public static final String AROUTER_TAG = "P_ARouter";

    public static void init(Application application, boolean printLog){
        if (printLog){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    public static void toMainActivity(Context context, Intent intent, String refer){
        // .build(Uri.parse(ARouterPath.COMMON_AROUTER_HOST + ARouterPath.PATH_MAIN_ACTIVITY));
        Postcard postcard = ARouter.getInstance().build(ARouterPath.PATH_MAIN_ACTIVITY);
        postcard.setName(refer);
        if (intent != null){
            postcard.with(intent.getExtras());
        }
        postcard.navigation(context);
    }

    public static void toTraditionColorActivity(Context context){
        ARouter.getInstance()
                .build(ARouterPath.PATH_TRADITION_COLOR_ACTIVITY)
                .withString("name", "p")
                .navigation(context);
    }
}
