package com.example.assignment6.ui.fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.view.View;

import com.example.assignment6.R;
import com.example.assignment6.data.ShoppingPreferences;
import com.example.assignment6.ui.fragment.dialog.AddDialogItem;
import com.example.assignment6.ui.fragment.dialog.AddDialogShoppingItem;
import com.example.assignment6.ui.fragment.generic.GenericShoppingFragment;
import com.example.assignment6.ui.recyclerview.MyShoppingItemRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.generic.FactoryShoppingRecyclerAdapter;


public class ShoppingItemFragment extends GenericShoppingFragment {

    public ShoppingItemFragment() {

    }

    @Override
    protected MyShoppingItemRecyclerAdapter createRecyclerViewAdapter() {
        return (MyShoppingItemRecyclerAdapter) FactoryShoppingRecyclerAdapter.getRecyclerAdapter(FactoryShoppingRecyclerAdapter.SHOPPING_ITEM, mContentResolver, this, this);
    }

    @Override
    protected AddDialogItem createItemDialog(ContentValues values) {
        SharedPreferences preferences =  getSharedPreferencesFromInterface();
        return new AddDialogShoppingItem(mAdapter, values, ShoppingPreferences.getParentID(preferences));
    }

    @Override
    protected RecyclerView findRecyclerView(View view) {
        return view.findViewById(R.id.recyclerview_shopping_sessions);
    }

    @Override
    protected int getColumnCount() {
        return 1;
    }

    @Override
    public void activateSubFragment(long id) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_view, new ItemInformationFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onStop() {
        ((MyShoppingItemRecyclerAdapter) mAdapter).updateTotalCost();
        super.onStop();
    }
}
