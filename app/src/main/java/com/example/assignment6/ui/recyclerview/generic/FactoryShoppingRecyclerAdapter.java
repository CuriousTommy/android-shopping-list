package com.example.assignment6.ui.recyclerview.generic;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.widget.Spinner;

import com.example.assignment6.ui.fragment.generic.FragmentTransactionInterface;
import com.example.assignment6.ui.fragment.generic.SharedPerferencesInterface;
import com.example.assignment6.ui.recyclerview.MyShoppingItemRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.MyShoppingSessionRecyclerAdapter;
import com.example.assignment6.ui.recyclerview.MyShoppingSessionWithSpinnerRecyclerAdapter;

public class FactoryShoppingRecyclerAdapter {
    public static final int SHOPPING_ITEM = 0;
    public static final int SHOPPING_SESSION = 1;
    public static final int SHOPPING_SESSION_WITH_SPINNER = 2;

    public interface FactoryShoppingInterface {
        void setBaseValue(ContentResolver contentResolver, FragmentTransactionInterface activateSubMenu, SharedPerferencesInterface sharedPreferencesInterface);
        void setShoppingItemValue(ContentResolver resolver, SharedPreferences sharedPreferences);
        void setShoppingSessionValue();
        void setShoppingSessionWithSpinnerValue(Spinner spinner);
    }

    public static TemplateShoppingRecyclerAdapter getRecyclerAdapter(int type, ContentResolver contentResolver, FragmentTransactionInterface activateSubMenu, SharedPerferencesInterface sharedPreferencesInterface, Object ...objects) {
        TemplateShoppingRecyclerAdapter adapter = null;

        switch (type) {
            case SHOPPING_ITEM:
                adapter = new MyShoppingItemRecyclerAdapter();
                adapter.setShoppingItemValue(contentResolver, sharedPreferencesInterface.getSharedPreferencesFromInterface());
                break;
            case SHOPPING_SESSION:
                adapter = new MyShoppingSessionRecyclerAdapter();
                adapter.setShoppingSessionValue();
                break;
            case SHOPPING_SESSION_WITH_SPINNER:
                adapter = new MyShoppingSessionWithSpinnerRecyclerAdapter();
                adapter.setShoppingSessionWithSpinnerValue((Spinner) objects[0]);
                adapter.setShoppingSessionValue();
                break;
        }

        if (adapter != null) {
            adapter.setBaseValue(contentResolver, activateSubMenu, sharedPreferencesInterface);
        }

        return adapter;
    }


}
