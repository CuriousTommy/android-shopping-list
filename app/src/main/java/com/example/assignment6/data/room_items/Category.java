package com.example.assignment6.data.room_items;


import android.content.ContentValues;
import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Category.TABLE_NAME)
public class Category {
    public static final String TABLE_NAME = "category";
    public static final String ATTRIBUTE_ID = "_id";
    public static final String ATTRIBUTE_STRING = "name";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ATTRIBUTE_ID)
    public long id;

    @ColumnInfo(name = ATTRIBUTE_STRING)
    public String name;

    public Category() {

    }

    public Category(long id, String title) {
        this(title);
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(Cursor mCursor) {
        this(
                mCursor.getLong(mCursor.getColumnIndex(Category.ATTRIBUTE_ID)),
                mCursor.getString(mCursor.getColumnIndex(Category.ATTRIBUTE_STRING))
        );
    }

    public Category(ContentValues values) {
        this(
                values.getAsLong(ATTRIBUTE_ID),
                values.getAsString(ATTRIBUTE_STRING)
        );
    }

    public ContentValues getContentValues() {
        final ContentValues values = new ContentValues();
        values.put(ATTRIBUTE_ID, id);
        values.put(ATTRIBUTE_STRING, name);
        return values;
    }
}
