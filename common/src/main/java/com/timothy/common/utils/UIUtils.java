package com.timothy.common.utils;

import android.content.Context;

public class UIUtils {

    public static int dp2px(Context context, int dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
