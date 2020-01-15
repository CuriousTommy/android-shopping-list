package com.example.assignment6.ui.recyclerview.generic;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.ui.fragment.generic.SharedPerferencesInterface;
import com.example.assignment6.ui.fragment.generic.FragmentTransactionInterface;
import com.example.assignment6.ui.recyclerview.viewholder.ShoppingViewHolderPlus;

import java.util.HashMap;

public abstract class TemplateShoppingRecyclerAdapter<A extends ShoppingViewHolderPlus,C>
        extends RecyclerView.Adapter<A>
        implements ItemManagementInterface<C>, FactoryShoppingRecyclerAdapter.FactoryShoppingInterface {
    //
    // Variables
    //

    protected ContentResolver mContentResolver;
    protected Cursor mCursor;
    protected FragmentTransactionInterface mActivateSubMenu;
    protected SharedPreferences mSharedPreferences;

    //
    // Constructors
    //

    public TemplateShoppingRecyclerAdapter() {}

    @Deprecated
    public TemplateShoppingRecyclerAdapter(ContentResolver contentResolver, FragmentTransactionInterface activateSubMenu, SharedPerferencesInterface sharedPreferencesInterface) {

    }

    @Override
    public void setBaseValue(ContentResolver contentResolver, FragmentTransactionInterface activateSubMenu, SharedPerferencesInterface sharedPreferencesInterface) {
        mSharedPreferences = sharedPreferencesInterface.getSharedPreferencesFromInterface();
        mContentResolver = contentResolver;
        mActivateSubMenu = activateSubMenu;
        mCursor = getCursor();
    }

    @Override
    public void setShoppingItemValue(ContentResolver resolver, SharedPreferences sharedPreferences) {

    }

    @Override
    public void setShoppingSessionValue() {

    }

    @Override
    public void setShoppingSessionWithSpinnerValue(Spinner spinner) {

    }

    //
    // Methods
    //

    @Override
    public abstract void onBindViewHolder(@NonNull A holder, int position);

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void updateCursor() {
        mCursor = getCursor();
        notifyDataSetChanged();
    }

    protected abstract Cursor getCursor();

}


