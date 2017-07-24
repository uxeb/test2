package com.test.test.presentation;

import android.support.v7.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private ItemClickListener itemClickListener;

    public void onItemClick(int itemId) {
        if(itemClickListener != null) {
            itemClickListener.onItemClick(itemId);
        }
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int itemId);
    }
}
