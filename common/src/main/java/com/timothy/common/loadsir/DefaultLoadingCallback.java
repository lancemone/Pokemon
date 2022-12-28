package com.timothy.common.loadsir;

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.kingja.loadsir.callback.Callback;
import com.timothy.common.R;
import com.timothy.common.utils.GlideUtils;

public class DefaultLoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_default_loadsir_loading;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
        AppCompatImageView loadingView = view.findViewById(R.id.loadsir_loading_image);
        GlideUtils.imageLoad(loadingView, com.timothy.resource.R.mipmap.ic_default_pikachu_loading_gif);
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
