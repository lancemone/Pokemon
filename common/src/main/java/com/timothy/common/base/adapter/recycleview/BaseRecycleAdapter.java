package com.timothy.common.base.adapter.recycleview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecycleAdapter extends RecyclerView.Adapter<BaseRecycleViewHolder> {

    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecycleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
