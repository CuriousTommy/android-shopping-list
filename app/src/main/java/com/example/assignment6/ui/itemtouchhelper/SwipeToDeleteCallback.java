package com.example.assignment6.ui.itemtouchhelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.ui.recyclerview.generic.ItemManagementInterface;
import com.example.assignment6.ui.recyclerview.viewholder.ShoppingViewHolderPlus;

public class SwipeToDeleteCallback<A extends ItemManagementInterface> extends ItemTouchHelper.Callback {
    private A adapter;

    public SwipeToDeleteCallback(A adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        ShoppingViewHolderPlus mViewHolder = (ShoppingViewHolderPlus) viewHolder;
        adapter.remove(mViewHolder.getID());
    }
}
