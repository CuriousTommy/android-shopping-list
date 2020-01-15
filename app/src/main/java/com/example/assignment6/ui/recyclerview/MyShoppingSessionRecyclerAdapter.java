package com.example.assignment6.ui.recyclerview;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.assignment6.R;
import com.example.assignment6.data.ShoppingContentProvider;
import com.example.assignment6.data.room_items.ShoppingSession;
import com.example.assignment6.ui.fragment.generic.SharedPerferencesInterface;
import com.example.assignment6.ui.fragment.generic.FragmentTransactionInterface;
import com.example.assignment6.ui.recyclerview.generic.TemplateShoppingRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.viewholder.ShoppingViewHolderPlus;


public class MyShoppingSessionRecyclerAdapter extends TemplateShoppingRecyclerAdapter<MyShoppingSessionRecyclerAdapter.ShoppingViewHolder, ShoppingSession> {
    public MyShoppingSessionRecyclerAdapter() {}

    @Override
    public void setShoppingSessionValue() {}

    //
    // Managing the element
    //


    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoppingsession_element, parent, false);
        return new ShoppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.mItem = new ShoppingSession(mCursor);
        }

        holder.setTitleString(holder.mItem.title);
        holder.setCostString(holder.mItem.cost);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivateSubMenu.activateSubFragment(holder.getID());
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mActivateSubMenu.activateDialogueItem(holder.mItem.getContentValues());
                return true;
            }
        });
    }

    @Override
    public void remove(long index) {
        mContentResolver.delete(ContentUris.withAppendedId(ShoppingContentProvider.URI_SHOPPINGSESSION_ITEM, index),
                null, null);
        mContentResolver.delete(ContentUris.withAppendedId(ShoppingContentProvider.URI_SHOPPINGITEM_PARENTID, index),
                null, null);
        updateCursor();
    }

    @Override
    public void update(ShoppingSession item) {
        mContentResolver.update(ShoppingContentProvider.URI_SHOPPINGSESSION_ITEM, item.getContentValues(), null, null);
        updateCursor();
    }

    @Override
    public void add(ShoppingSession item) {
        mContentResolver.insert(ShoppingContentProvider.URI_SHOPPINGSESSION_ITEM, item.getContentValues());
        updateCursor();
    }

    //
    // Database Management
    //

    protected Cursor getCursor() {
        return mContentResolver.query(ShoppingContentProvider.URI_SHOPPINGSESSION_ITEM,
                null, null, null, null, null);
    }

    //
    // ShoppingViewHolder Class
    //

    public class ShoppingViewHolder extends ShoppingViewHolderPlus<ShoppingSession> {
        public ShoppingViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.shopping_title);
            mCost = view.findViewById(R.id.shopping_total_cost);
        }

        @Override
        public long getID() {
            return mItem.id;
        }
    }
}
