package com.example.assignment6.ui.fragment;


import android.content.ContentResolver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.assignment6.R;
import com.example.assignment6.ui.fragment.dialog.AddDialogCategoryItem;
import com.example.assignment6.ui.recyclerview.CategoryCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageCategory extends Fragment implements View.OnClickListener {
    private ContentResolver mContentResolver;
    private CategoryCursorAdapter mAdapter;
    private Spinner mSpinner;

    public ManageCategory() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentResolver = requireContext().getContentResolver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_category, container, false);

        Button mAddCategory = view.findViewById(R.id.category_add_category);
        mAddCategory.setOnClickListener(this);

        mSpinner = view.findViewById(R.id.category_spinner);
        mAdapter = new CategoryCursorAdapter(requireContext(), mContentResolver);
        mSpinner.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        new AddDialogCategoryItem(mAdapter, null).show(requireFragmentManager().beginTransaction(), null);
        mAdapter.updateCursor();
    }
}
