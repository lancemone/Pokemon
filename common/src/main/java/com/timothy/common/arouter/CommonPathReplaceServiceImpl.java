package com.timothy.common.arouter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.orhanobut.logger.Logger;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.arouter.CommonPathReplaceServiceImpl
 * @Author: MoTao
 * @Date: 2023-03-08
 * <p>
 * 重写跳转URL  实现PathReplaceService接口，并加上一个Path内容任意的注解即可
 * <p/>
 */

@Route(path = ARouterPath.PATH_COMMON_PATH_REPLACE_SERVICE, name = "CommonPathReplaceServiceImpl")  // 必须标明注解
public class CommonPathReplaceServiceImpl implements PathReplaceService {

    @Override
    public String forString(String path) {
        Log.d(ARouterManager.AROUTER_TAG,"CommonPathReplaceServiceImpl string path:" + path);
        return path;
    }

    @Override
    public Uri forUri(Uri uri) {
        Log.d(ARouterManager.AROUTER_TAG, "CommonPathReplaceServiceImpl uri path:" + uri);
        return uri;
    }

    @Override
    public void init(Context context) {
        Log.d(ARouterManager.AROUTER_TAG,"CommonPathReplaceServiceImpl init: " + context.getClass().getSimpleName());
    }
}
