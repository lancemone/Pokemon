package com.timothy.common.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

abstract public class BaseActivity<DB extends ViewDataBinding> extends AppCompatActivity {

    protected static long ACTIVITY_ANIM_DURATION = 300;
    protected DB mDataBinding;
    protected LoadService<?> mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mDataBinding.setLifecycleOwner(this);
        initView();
        if (isNeedToInitLoadsir()) {
            registerLoadsir();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataBinding.unbind();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化data binding后调用
     */
    protected abstract void initView();

    /**
     * initView 后调用，默认注册当前页面根布局
     */
    protected void registerLoadsir() {
        mLoadService = LoadSir.getDefault().register(mDataBinding.getRoot(), (Callback.OnReloadListener) v -> reloadData());
    }

    protected void beforeOnCreate() {
        MaterialSharedAxis materialSharedAxis = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        materialSharedAxis.setDuration(ACTIVITY_ANIM_DURATION);
        getWindow().setEnterTransition(materialSharedAxis);
        getWindow().setExitTransition(materialSharedAxis);
    }

    protected void reloadData() {
    }

    protected void loadSuccess() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    protected boolean isNeedToInitLoadsir() {
        return false;
    }
}
