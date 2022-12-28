package com.timothy.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.timothy.common.data.base.DataStatus;
import com.timothy.common.loadsir.DefaultEmptyCallback;
import com.timothy.common.loadsir.DefaultLoadingCallback;
import com.timothy.common.loadsir.DefaultNetworkErrorCallback;

abstract public class BaseMVVMActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<DB>{
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        observeCommonData();
        observeData();
        initData();
    }

    protected abstract VM getViewModel();

    protected abstract void observeData();

    protected void initData(){
        mViewModel.initData();
    }

    protected void observeCommonData(){
        if (mLoadService != null){
            mViewModel.getDataStatus().observe(this, new Observer<DataStatus>() {
                @Override
                public void onChanged(DataStatus dataStatus) {
                    onDataStatusChange(dataStatus);
                }
            });
        }
    }

    protected void onDataStatusChange(DataStatus dataStatus){
        if (dataStatus == null || mLoadService == null){
            return;
        }
        switch (dataStatus){
            case START:
                if (mLoadService.getCurrentCallback() != DefaultLoadingCallback.class){
                    mLoadService.showCallback(DefaultLoadingCallback.class);
                }
                break;
            case SUCCESS:
                mLoadService.showSuccess();
                break;
            case EMPTY_DATA:
                mLoadService.showCallback(DefaultEmptyCallback.class);
                break;
            case NET_ERROR:
                mLoadService.showCallback(DefaultNetworkErrorCallback.class);
                break;
        }
    }
}
