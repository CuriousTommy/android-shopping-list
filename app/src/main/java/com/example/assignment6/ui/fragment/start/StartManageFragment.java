package com.example.assignment6.ui.fragment.start;

import androidx.fragment.app.Fragment;

import com.example.assignment6.MainFragment;
import com.example.assignment6.ui.fragment.ManageCategory;

public class StartManageFragment extends MainFragment {
    @Override
    protected Fragment getNewFragment() {
        return new ManageCategory();
    }
}
