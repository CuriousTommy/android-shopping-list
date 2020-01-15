package com.example.assignment6.ui.fragment.generic;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.R;
import com.example.assignment6.data.ShoppingPreferences;
import com.example.assignment6.ui.GridDecoration;
import com.example.assignment6.ui.fragment.dialog.AddDialogItem;
import com.example.assignment6.ui.itemtouchhelper.SwipeToDeleteCallback;
import com.example.assignment6.ui.recyclerview.CategoryCursorAdapter;
import com.example.assignment6.ui.recyclerview.generic.FactoryShoppingRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.generic.TemplateShoppingRecyclerAdapter;

public abstract class GenericShoppingFragment<A extends TemplateShoppingRecyclerAdapter>
        extends Fragment
        implements FragmentTransactionInterface, SharedPerferencesInterface, AdapterView.OnItemSelectedListener {
    public static final String BUNDLE_USE_SPINNER = "BUNDLE_USE_SPINNER";

    protected Spinner mSpinner;
    protected ContentResolver mContentResolver;
    protected A mAdapter;
    protected FactoryShoppingRecyclerAdapter mFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentResolver = requireContext().getContentResolver();
        mFactory = new FactoryShoppingRecyclerAdapter();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_template, container, false);
        Context context = view.getContext();

        mSpinner = view.findViewById(R.id.categories_spinner);
        mSpinner.setVisibility(getSpinnerVisibility());
        if (getSpinnerVisibility() != View.GONE) {
            mSpinner.setAdapter(new CategoryCursorAdapter(requireContext(), requireContext().getContentResolver()));
            mSpinner.setOnItemSelectedListener(this);
        }



        RecyclerView recyclerView = findRecyclerView(view);
        mAdapter = createRecyclerViewAdapter();
        int mColumnCount = getColumnCount();

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        recyclerView.addItemDecoration(new GridDecoration());
        recyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(new SwipeToDeleteCallback<>(mAdapter)).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.manage_item_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item_menu:
                activateDialogueItem(null);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void activateDialogueItem(ContentValues values) {
        AddDialogItem dialog = createItemDialog(values);

        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        dialog.show(ft, null);
    }

    public SharedPreferences getSharedPreferencesFromInterface() {
        return requireContext().getSharedPreferences(ShoppingPreferences.name, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getSharedPreferencesEditorFromInterface() {
        return requireContext().getSharedPreferences(ShoppingPreferences.name, Context.MODE_PRIVATE).edit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.updateCursor();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected abstract A createRecyclerViewAdapter();
    protected abstract AddDialogItem createItemDialog(ContentValues values);
    protected abstract RecyclerView findRecyclerView(View view);
    protected abstract int getColumnCount();

    protected int getSpinnerVisibility() {
        return View.GONE;
    }
}
