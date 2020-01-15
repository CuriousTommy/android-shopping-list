package com.example.assignment6.ui.recyclerview;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.widget.Spinner;

import com.example.assignment6.data.ShoppingContentProvider;
import com.example.assignment6.ui.fragment.generic.FragmentTransactionInterface;
import com.example.assignment6.ui.fragment.generic.SharedPerferencesInterface;

public class MyShoppingSessionWithSpinnerRecyclerAdapter extends MyShoppingSessionRecyclerAdapter {
    Spinner mSpinner;

    public MyShoppingSessionWithSpinnerRecyclerAdapter() {}

    @Override
    public void setShoppingSessionWithSpinnerValue(Spinner spinner) {
        mSpinner = spinner;
    }

    @Override
    protected Cursor getCursor() {
        return mContentResolver.query(ContentUris.withAppendedId(ShoppingContentProvider.URI_SHOPPINGSESSION_CATEGORY, mSpinner.getSelectedItemId()),
                null, null, null, null, null);
    }
}
