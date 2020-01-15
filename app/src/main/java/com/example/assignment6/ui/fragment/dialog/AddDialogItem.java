package com.example.assignment6.ui.fragment.dialog;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.example.assignment6.R;
import com.example.assignment6.ui.recyclerview.CategoryCursorAdapter;
import com.example.assignment6.ui.recyclerview.generic.ItemManagementInterface;

public abstract class AddDialogItem<A> extends DialogFragment implements View.OnClickListener {
    protected ItemManagementInterface<A> mAdapter;
    protected EditText mEditableTitle;
    protected EditText mEditableCost;
    protected Spinner mSelectableCategory;
    protected A mItem;

    public AddDialogItem(ItemManagementInterface adapter, ContentValues values) {
        mAdapter = adapter;

        if (values != null) {
            mItem = createItemFromContentValues(values);
        } else {
            mItem = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.dialog_fragment_shopping_session, container, false);
        Button cancel = view.findViewById(R.id.button_cancel);
        Button ok = view.findViewById(R.id.button_ok);
        mEditableTitle = view.findViewById(R.id.item_title);
        mEditableCost = view.findViewById(R.id.item_cost);
        mSelectableCategory = view.findViewById(R.id.item_category);

        mSelectableCategory.setAdapter(new CategoryCursorAdapter(requireContext(), requireContext().getContentResolver()));

        if (mItem != null) {
            getValuesFromItem(mItem);
        }

        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_ok:
                if (mItem == null) {
                    addItemToDatabase();
                } else {
                    updateItemInDatabase(mItem);
                }
                dismiss();
                break;
        }
    }

    protected abstract void addItemToDatabase();
    protected abstract void updateItemInDatabase(A item);
    protected abstract A createItemFromContentValues(ContentValues values);

    protected abstract void setValuesToItem(A item);
    protected abstract void getValuesFromItem(A item);
}
