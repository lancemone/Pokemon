package com.timothy.common.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.arouter.LoginInterceptor
 * @Author: MoTao
 * @Date: 2023-03-09
 * <p>
 *     跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
 * <p/>
 */

@Interceptor(priority = 10, name = "LoginInterceptor")   // 多个拦截器会按优先级顺序依次执行
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.d(ARouterManager.AROUTER_TAG,"LoginInterceptor process: " + postcard.getUri());
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        Log.d(ARouterManager.AROUTER_TAG,"LoginInterceptor init: " + context.getClass().getSimpleName());
    }
}
