package com.example.assignment6.ui.fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.assignment6.R;
import com.example.assignment6.data.ShoppingPreferences;
import com.example.assignment6.ui.fragment.dialog.AddDialogItem;
import com.example.assignment6.ui.fragment.dialog.AddDialogShoppingSession;
import com.example.assignment6.ui.fragment.generic.GenericShoppingFragment;
import com.example.assignment6.ui.recyclerview.MyShoppingSessionRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.MyShoppingSessionWithSpinnerRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.generic.FactoryShoppingRecyclerAdapter;

public class ShoppingSessionFragment extends GenericShoppingFragment {
    private boolean use_spinner;

    public ShoppingSessionFragment() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        use_spinner = bundle.getBoolean(GenericShoppingFragment.BUNDLE_USE_SPINNER);
    }

    @Override
    protected MyShoppingSessionRecyclerAdapter createRecyclerViewAdapter() {
        if (!use_spinner) {
            return (MyShoppingSessionRecyclerAdapter) FactoryShoppingRecyclerAdapter.getRecyclerAdapter(
                    FactoryShoppingRecyclerAdapter.SHOPPING_SESSION,
                    mContentResolver,
                    this,
                    this
            );
        } else {
            return (MyShoppingSessionWithSpinnerRecyclerAdapter) FactoryShoppingRecyclerAdapter.getRecyclerAdapter(
                    FactoryShoppingRecyclerAdapter.SHOPPING_SESSION_WITH_SPINNER,
                    mContentResolver,
                    this,
                    this,
                    mSpinner
            );
        }
    }

    @Override
    protected AddDialogItem createItemDialog(ContentValues values) {
        return new AddDialogShoppingSession(mAdapter, values);
    }

    @Override
    protected RecyclerView findRecyclerView(View view) {
        return view.findViewById(R.id.recyclerview_shopping_sessions);
    }

    @Override
    protected int getColumnCount() {
        return 2;
    }

    @Override
    public void activateSubFragment(long id) {
        SharedPreferences.Editor editor = getSharedPreferencesEditorFromInterface();
        editor.clear()
                .putLong(ShoppingPreferences.parent_id, id)
                .apply();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_view, new ShoppingItemFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected int getSpinnerVisibility() {
        if (!use_spinner) {
            return super.getSpinnerVisibility();
        } else {
            return View.VISIBLE;
        }
    }
}
