package com.example.assignment6.ui.fragment.dialog;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment6.data.room_items.ShoppingSession;
import com.example.assignment6.ui.recyclerview.generic.ItemManagementInterface;

public class AddDialogShoppingSession extends AddDialogItem<ShoppingSession> {
    public AddDialogShoppingSession(ItemManagementInterface adapter, ContentValues values) {
        super(adapter, values);
    }

    @Override
    protected void addItemToDatabase() {
        ShoppingSession ss = new ShoppingSession();
        setValuesToItem(ss);
        mAdapter.add(ss);
    }

    @Override
    protected void updateItemInDatabase(ShoppingSession item) {
        setValuesToItem(item);
        mAdapter.update(item);
    }

    @Override
    protected ShoppingSession createItemFromContentValues(ContentValues values) {
        return ShoppingSession.createShoppingSession(values);
    }

    @Override
    protected void setValuesToItem(ShoppingSession item) {
        item.title = mEditableTitle.getText().toString();
        item.cost = 0;
        item.category_id = mSelectableCategory.getSelectedItemId();
    }

    @Override
    protected void getValuesFromItem(ShoppingSession item) {
        mEditableTitle.setText(item.title);
        for (int position = 0; position < mSelectableCategory.getCount(); position++) {
            if (mSelectableCategory.getItemIdAtPosition(position) == item.category_id) {
                mSelectableCategory.setSelection(position);
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = super.onCreateView(inflater, container, savedInstance);
        mEditableCost.setVisibility(View.GONE);
        return view;
    }
}
