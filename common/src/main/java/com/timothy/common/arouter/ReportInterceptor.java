package com.timothy.common.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.arouter.ReportInterceptor
 * @Author: MoTao
 * @Date: 2023-03-09
 * <p>
 *     拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
 * <p/>
 */

@Interceptor(priority = 1, name = "ReportInterceptor")
public class ReportInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.d(ARouterManager.AROUTER_TAG,"ReportInterceptor process: " + postcard.getUri());
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        Log.d(ARouterManager.AROUTER_TAG,"ReportInterceptor init: " + context.getClass().getSimpleName());
    }
}
