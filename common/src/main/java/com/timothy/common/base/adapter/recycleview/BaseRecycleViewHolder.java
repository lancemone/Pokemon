package com.timothy.common.base.adapter.recycleview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timothy.common.data.base.BaseData;

public abstract class BaseRecycleViewHolder extends RecyclerView.ViewHolder {

    public BaseRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract <T extends BaseData> void bindData(T data);
}
