package com.timothy.common.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.base.fragment.BaseDBFragment
 * @Author: MoTao
 * @Date: 2023-03-10
 * <p>
 * <p/>
 */
public abstract class BaseDBFragment<DB extends ViewDataBinding> extends BaseFragment {

    protected DB mDataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, fragmentLayoutId(), container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDataBinding != null){
            mDataBinding.unbind();
            mDataBinding = null;
        }
    }
}
