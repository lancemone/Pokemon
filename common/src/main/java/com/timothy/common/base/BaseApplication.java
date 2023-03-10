package com.timothy.common.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.timothy.common.arouter.ARouterManager;
import com.timothy.common.loadsir.DefaultEmptyCallback;
import com.timothy.common.loadsir.DefaultLoadingCallback;
import com.timothy.common.loadsir.DefaultNetworkErrorCallback;
import com.timothy.common.manager.AppManager;
import com.timothy.common.manager.MMKVManager;
import com.timothy.common.sundries.PokemonLogAdapter;

public abstract class BaseApplication extends Application {

    static {

        Logger.addLogAdapter(new PokemonLogAdapter());
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new MaterialHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });

        LoadSir.beginBuilder()
                .addCallback(new DefaultEmptyCallback())
                .addCallback(new DefaultNetworkErrorCallback())
                .addCallback(new DefaultLoadingCallback())
                .setDefaultCallback(DefaultLoadingCallback.class)
                .commit();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBase();
        init();
    }

    private void initBase() {
        Logger.d("Start initBase Options");
        AppManager.getInstance().register(this);
        ARouterManager.init(this, true);
        MMKVManager.initMMKV(this);
    }

    protected abstract void init();
}
