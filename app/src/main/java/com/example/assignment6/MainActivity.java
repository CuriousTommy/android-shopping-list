package com.example.assignment6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.assignment6.ui.fragment.ShoppingSessionFragment;
import com.example.assignment6.ui.fragment.start.StartManageFragment;
import com.example.assignment6.ui.fragment.start.StartShoppingSessionFragment;
import com.example.assignment6.ui.fragment.start.StartShoppingSessionWithSpinnerFragment;
import com.example.assignment6.ui.fragment.toolbar.TabSelectionManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button mViewAll = findViewById(R.id.button_show_all);
        Button mByCategory = findViewById(R.id.button_show_category);
        Button mEditCategory = findViewById(R.id.button_edit_category);

        mViewAll.setOnClickListener(this);
        mByCategory.setOnClickListener(this);
        mEditCategory.setOnClickListener(this);

//        ViewPager viewPager = findViewById(R.id.main_menu_view_pager);
//        TabLayout tabs = findViewById(R.id.tabs);

//        TabSelectionManager tabSelectionManager = new TabSelectionManager(getBaseContext(), getSupportFragmentManager());
//        viewPager.setAdapter(tabSelectionManager);
//        tabs.setupWithViewPager(viewPager);

//        ContentResolver resolver = getContentResolver();
//        Uri url = ShoppingContentProvider.URI_SHOPPINGSESSION_ITEM;
//        resolver.insert(url, ShoppingSession.createContentValues(new ShoppingSession("Costco", 23.54)));
//        resolver.insert(url, ShoppingSession.createContentValues(new ShoppingSession("Safeway", 29.56)));
//        resolver.insert(url, ShoppingSession.createContentValues(new ShoppingSession("Raley", 19.53)));

//        String[] projection = null;
//        String selection = null;
//        String[] selectionArgs = null;
//        String sortOrder = null;
//        Cursor cursor = resolver.query(url, projection, selection, selectionArgs, sortOrder);
//
//
//        List<ShoppingSession> test = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            ShoppingSession temp = new ShoppingSession();
//            temp.id = cursor.getLong(cursor.getColumnIndex(ShoppingSession.ATTRIBUTE_ID));
//            temp.title = cursor.getString(cursor.getColumnIndex(ShoppingSession.ATTRIBUTE_TITLE));
//            temp.cost = cursor.getDouble(cursor.getColumnIndex(ShoppingSession.ATTRIBUTE_COST));
//            test.add(temp);
//        }
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.button_show_all:
                i = new Intent(this, StartShoppingSessionFragment.class);
                startActivity(i);
                break;
            case R.id.button_show_category:
                i = new Intent(this, StartShoppingSessionWithSpinnerFragment.class);
                startActivity(i);
                break;
            case R.id.button_edit_category:
                i = new Intent(this, StartManageFragment.class);
                startActivity(i);
        }
    }
}
