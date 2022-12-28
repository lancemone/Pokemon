package com.timothy.common.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public abstract class BaseMVVMSmartRefreshActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseMVVMActivity<DB, VM> implements OnRefreshLoadMoreListener {

    protected SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSmartRefreshLayout(getSmartRefreshLayout());
    }

    protected void initSmartRefreshLayout(SmartRefreshLayout refreshLayout){
        mSmartRefreshLayout = refreshLayout;
        refreshLayout.setEnableLoadMore(isEnableLoadMore());
        refreshLayout.setEnableAutoLoadMore(isEnableAutoLoadMore());
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @NonNull
    abstract SmartRefreshLayout getSmartRefreshLayout();

    protected boolean isEnableLoadMore(){
        return true;
    }

    protected boolean isEnableAutoLoadMore(){
        return true;
    }
}
