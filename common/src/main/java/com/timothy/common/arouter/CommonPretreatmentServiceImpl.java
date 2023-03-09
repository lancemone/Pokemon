package com.timothy.common.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PretreatmentService;
import com.orhanobut.logger.Logger;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.arouter.CommonPretreatmentServiceImpl
 * @Author: MoTao
 * @Date: 2023-03-08
 * <p>
 * 预处理服务 实现PretreatmentService 接口，并加上一个Path内容任意的注解即可
 * <p/>
 */

@Route(path = ARouterPath.PATH_COMMON_PRETREATMENT_SERVICE, name = "CommonPretreatmentService")
public class CommonPretreatmentServiceImpl implements PretreatmentService {

    @Override
    public boolean onPretreatment(Context context, Postcard postcard) {
        // 跳转前预处理，如果需要自行处理跳转，该方法返回 false 即可
        Log.d(ARouterManager.AROUTER_TAG, "CommonPretreatmentServiceImpl postcard name:" + postcard.getName());
        return true;
    }

    @Override
    public void init(Context context) {
        Log.d(ARouterManager.AROUTER_TAG, "CommonPretreatmentServiceImpl init: " + context.getClass().getSimpleName());
    }
}
