package com.example.assignment6.ui.fragment.dialog;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment6.data.room_items.Category;
import com.example.assignment6.ui.recyclerview.generic.ItemManagementInterface;

public class AddDialogCategoryItem extends AddDialogItem<Category> {
    public AddDialogCategoryItem(ItemManagementInterface adapter, ContentValues values) {
        super(adapter, values);
    }

    @Override
    protected void addItemToDatabase() {
        mAdapter.add(new Category(
                mEditableTitle.getText().toString()
        ));
    }

    @Override
    protected void updateItemInDatabase(Category item) {
        mAdapter.update(item);
    }

    @Override
    protected Category createItemFromContentValues(ContentValues values) {
        return new Category(values);
    }

    @Override
    protected void setValuesToItem(Category item) {
        item.name = mEditableTitle.getText().toString();
    }

    @Override
    protected void getValuesFromItem(Category item) {
        mEditableTitle.setText(item.name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = super.onCreateView(inflater, container, savedInstance);
        mEditableCost.setVisibility(View.GONE);
        mSelectableCategory.setVisibility(View.GONE);
        return view;
    }
}
