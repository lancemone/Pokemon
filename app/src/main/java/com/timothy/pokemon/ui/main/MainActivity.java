package com.timothy.pokemon.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.timothy.common.arouter.ARouterManager;
import com.timothy.common.arouter.ARouterPath;
import com.timothy.common.base.BaseActivity;
import com.timothy.pokemon.R;
import com.timothy.pokemon.databinding.ActivityMainBinding;
import com.timothy.rotatingtext.Rotatable;


@Route(path = ARouterPath.PATH_MAIN_ACTIVITY, name = "MainActivity")
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
//                Uri uri = Uri.parse("pokemon://m.timothy.pokemon/app/tradition/activity_tradition_color");
//                ARouter.getInstance().build(uri).navigation();
            }
        });
//        mDataBinding.splitterImage.setImageResource(com.timothy.resource.R.mipmap.sample_liutijianbian_1);
        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Word", "Word01", "Word02");
        rotatable.setSize(35);
        rotatable.setAnimationDuration(5000);
        rotatable.setCenter(true);
        rotatable.setApplyHorizontal(false);
        mDataBinding.rotatingView.setContent("This is ?", rotatable);
    }

    @Override
    protected boolean isNeedToInitLoadsir() {
        return false;
    }
}