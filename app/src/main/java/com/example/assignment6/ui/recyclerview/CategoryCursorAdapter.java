package com.example.assignment6.ui.recyclerview;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.example.assignment6.R;
import com.example.assignment6.data.ShoppingContentProvider;
import com.example.assignment6.data.room_items.Category;
import com.example.assignment6.ui.recyclerview.generic.ItemManagementInterface;



public class CategoryCursorAdapter extends SimpleCursorAdapter
        implements ItemManagementInterface<Category> {

    private ContentResolver mContentResolver;

    public CategoryCursorAdapter(Context context, ContentResolver resolver) {
        super(
                context,
                R.layout.spinner_element,
                resolver.query(
                        ShoppingContentProvider.URI_CATEGORY_ITEM,
                        null,
                        null,
                        null,
                        null
                ),
                new String[]{Category.ATTRIBUTE_STRING},
                new int[]{R.id.spinner_text_name},
                FLAG_REGISTER_CONTENT_OBSERVER
        );
        setDropDownViewResource(R.layout.spinner_element);
        mContentResolver = resolver;
    }

    @Override
    public void remove(long index) {
        mContentResolver.delete(ContentUris.withAppendedId(ShoppingContentProvider.URI_CATEGORY_ITEM, index),
                null, null);
        updateCursor();
    }

    @Override
    public void update(Category item) {
        mContentResolver.update(ShoppingContentProvider.URI_CATEGORY_ITEM, item.getContentValues(),
                null, null);
        updateCursor();
    }

    @Override
    public void add(Category item) {
        mContentResolver.insert(ShoppingContentProvider.URI_CATEGORY_ITEM, item.getContentValues());
        updateCursor();
    }

    public void updateCursor() {
        changeCursor(
                mContentResolver.query(
                        ShoppingContentProvider.URI_CATEGORY_ITEM,
                        null,
                        null,
                        null,
                        null
                )
        );
    }
}
