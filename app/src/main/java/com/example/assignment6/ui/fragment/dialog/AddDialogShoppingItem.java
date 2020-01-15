package com.example.assignment6.ui.fragment.dialog;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment6.data.room_items.ShoppingItem;
import com.example.assignment6.ui.recyclerview.generic.ItemManagementInterface;

import java.util.Locale;

public class AddDialogShoppingItem extends AddDialogItem<ShoppingItem> {
    private long parent_id;

    public AddDialogShoppingItem(ItemManagementInterface adapter, ContentValues values, long parent_id) {
        super(adapter, values);
        this.parent_id = parent_id;
    }

    @Override
    protected void addItemToDatabase() {
        ShoppingItem item = new ShoppingItem();
        setValuesToItem(item);
        mAdapter.add(item);
    }

    @Override
    protected void updateItemInDatabase(ShoppingItem item) {
        setValuesToItem(item);
        mAdapter.update(item);
    }

    @Override
    protected ShoppingItem createItemFromContentValues(ContentValues values) {
        return new ShoppingItem(values);
    }

    @Override
    protected void setValuesToItem(ShoppingItem item) {
        item.parent_id = parent_id;
        item.title = mEditableTitle.getText().toString();
        item.cost = Double.parseDouble(mEditableCost.getText().toString());
    }

    @Override
    protected void getValuesFromItem(ShoppingItem item) {
        mEditableTitle.setText(item.title);
        mEditableCost.setText(String.format(Locale.US,"%f", item.cost));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = super.onCreateView(inflater, container, savedInstance);
        mSelectableCategory.setVisibility(View.GONE);
        return view;
    }
}
