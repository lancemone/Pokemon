package com.timothy.common.utils;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;

public class GlideUtils {

    public static void imageLoad(ImageView imageView, @DrawableRes int resourceId){
        if (imageView == null || imageView.getContext() == null){
            return;
        }
        Glide.with(imageView).load(resourceId).into(imageView);
    }
}
