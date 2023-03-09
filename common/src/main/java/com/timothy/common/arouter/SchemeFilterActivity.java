package com.timothy.common.arouter;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.arouter.SchemeFilterActivity
 * @Author: MoTao
 * @Date: 2023-03-08
 * <p>
 *     用于监听Scheme事件,之后直接把url传递给ARouter
 * <p/>
 */
public class SchemeFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        Log.d(ARouterManager.AROUTER_TAG, "SchemeFilterActivity Intent Uri:" + uri);
        ARouter.getInstance().build(uri).navigation();
        finish();
    }
}
