package com.timothy.common.base.fragment;

import androidx.databinding.ViewDataBinding;

import com.timothy.common.base.BaseViewModel;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.base.fragment.BaseDBWithVHFragment
 * @Author: MoTao
 * @Date: 2023-03-10
 * <p>
 * <p/>
 */
abstract public class BaseDBWithVHFragment<DB extends ViewDataBinding, VH extends BaseViewModel> extends BaseDBFragment<DB>{

    protected VH mViewHolder;
    @Override
    protected void afterCreate() {
        super.afterCreate();
        mViewHolder = getViewHolder();
    }

    abstract VH getViewHolder();
}
