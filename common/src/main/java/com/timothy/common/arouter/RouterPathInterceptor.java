package com.timothy.common.arouter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.arouter.RouterPathInterceptor
 * @Author: MoTao
 * @Date: 2023-03-09
 * <p>
 * <p/>
 */

@Interceptor(priority = 2, name = "RouterPathInterceptor")
public class RouterPathInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        if (!TextUtils.isEmpty(path)){
            Log.d(ARouterManager.AROUTER_TAG, "RouterPathInterceptor process path: " + path);
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {

    }
}
