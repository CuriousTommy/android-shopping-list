package com.example.assignment6.ui.recyclerview;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.example.assignment6.R;
import com.example.assignment6.data.ShoppingContentProvider;
import com.example.assignment6.data.ShoppingPreferences;
import com.example.assignment6.data.room_items.ShoppingItem;
import com.example.assignment6.data.room_items.ShoppingSession;
import com.example.assignment6.ui.recyclerview.generic.TemplateShoppingRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.viewholder.ShoppingViewHolderPlus;


public class MyShoppingItemRecyclerAdapter extends TemplateShoppingRecyclerAdapter<MyShoppingItemRecyclerAdapter.ShoppingViewHolder, ShoppingItem> {
    private ShoppingSession shoppingSession;

    public MyShoppingItemRecyclerAdapter() {

    }

    @Override
    public void setShoppingItemValue(ContentResolver resolver, SharedPreferences sharedPreferences) {
        Cursor temp_cursor = resolver.query(
                ContentUris.withAppendedId(
                        ShoppingContentProvider.URI_SHOPPINGSESSION_ITEM,  ShoppingPreferences.getParentID(sharedPreferences)
                ),
                null, null, null, null
        );

        if (temp_cursor.moveToFirst()) {
            shoppingSession = new ShoppingSession(temp_cursor);
        } else {
            shoppingSession = null;
        }
    }

    //
    //
    //

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingitem_element, parent, false);
        return new ShoppingViewHolder(view);
    }

    
    @Override
    public void onBindViewHolder(@NonNull final ShoppingViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.mItem = new ShoppingItem(mCursor);
        }

        holder.setTitleString(holder.mItem.title);
        holder.setCostString(holder.mItem.cost);
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mActivateSubMenu.activateSubFragment(holder.getID());
//            }
//        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mActivateSubMenu.activateDialogueItem(holder.mItem.getContentValues());
                return true;
            }
        });
//        holder.setImage(null);
    }

    //
    //
    //

    @Override
    protected Cursor getCursor() {
        long parent_id = ShoppingPreferences.getParentID(mSharedPreferences);
        return mContentResolver.query(ContentUris.withAppendedId(ShoppingContentProvider.URI_SHOPPINGITEM_PARENTID, parent_id), null, null, null, null);
    }

    public void updateTotalCost() {
        if (shoppingSession != null) {
            Cursor value = mContentResolver.query(ContentUris.withAppendedId(ShoppingContentProvider.URI_SHOPPINGITEM_SUMMARY_COST, shoppingSession.id),
                    null, null, null, null);
            if (value.moveToFirst()) {
                shoppingSession.cost = value.getDouble(0);
                mContentResolver.update(ShoppingContentProvider.URI_SHOPPINGSESSION_ITEM, shoppingSession.getContentValues(),
                        null, null);
            }
        }
    }

    //
    //
    //

    @Override
    public void remove(long index) {
        mContentResolver.delete(ContentUris.withAppendedId(ShoppingContentProvider.URI_SHOPPINGITEM_ITEM, index),
                null, null);
        updateCursor();
    }

    @Override
    public void update(ShoppingItem item) {
        mContentResolver.update(ShoppingContentProvider.URI_SHOPPINGITEM_ITEM, item.getContentValues(),
                null, null);
        updateCursor();
    }

    @Override
    public void add(ShoppingItem item) {
        mContentResolver.insert(ShoppingContentProvider.URI_SHOPPINGITEM_ITEM, item.getContentValues());
        updateCursor();
    }





    //
    //
    //

    public class ShoppingViewHolder extends ShoppingViewHolderPlus<ShoppingItem> {
        public ShoppingViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.shopping_item_name);
            mCost = view.findViewById(R.id.shopping_item_number);
            mImage = view.findViewById(R.id.shopping_item_image);
        }

        @Override
        public long getID() {
            return mItem.id;
        }

    }
}
