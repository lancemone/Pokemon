package com.timothy.pokemon.ui.main;

import android.annotation.SuppressLint;
import android.provider.Settings;
import android.view.View;

import com.timothy.common.arouter.ARouterManager;
import com.timothy.common.base.BaseActivity;
import com.timothy.pokemon.R;
import com.timothy.pokemon.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        @SuppressLint("HardwareIds")
        String androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        mDataBinding.textTime.setText(androidId);
        mDataBinding.setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterManager.toTraditionColorActivity(getBaseContext());
            }
        });
//        mDataBinding.splitterImage.setImageResource(com.timothy.resource.R.mipmap.sample_liutijianbian_1);
    }

    @Override
    protected boolean isNeedToInitLoadsir() {
        return false;
    }
}