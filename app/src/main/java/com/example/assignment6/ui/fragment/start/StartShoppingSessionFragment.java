package com.example.assignment6.ui.fragment.start;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.assignment6.MainFragment;
import com.example.assignment6.ui.fragment.ShoppingSessionFragment;
import com.example.assignment6.ui.fragment.generic.GenericShoppingFragment;

public class StartShoppingSessionFragment extends MainFragment {
    @Override
    protected Fragment getNewFragment() {
        Fragment fragment = new ShoppingSessionFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(GenericShoppingFragment.BUNDLE_USE_SPINNER, false);

        fragment.setArguments(bundle);
        return fragment;
    }
}
