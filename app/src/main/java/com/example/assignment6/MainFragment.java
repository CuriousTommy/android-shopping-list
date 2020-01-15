package com.example.assignment6;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment6.R;
import com.example.assignment6.ui.fragment.ShoppingSessionFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MainFragment extends AppCompatActivity {
    public MainFragment() {
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_view, getNewFragment());
        ft.commit();
    }

    protected abstract Fragment getNewFragment();
}
