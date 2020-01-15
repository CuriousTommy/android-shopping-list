package com.example.assignment6.data.room_items;


import android.content.ContentValues;
import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = ShoppingSession.TABLE_NAME)
public class ShoppingSession {
    @Ignore
    public static final String ATTRIBUTE_ID = "id";
    @Ignore
    public static final String ATTRIBUTE_CATEGORY_ID = "category_id";
    @Ignore
    public static final String ATTRIBUTE_TITLE = "title";
    @Ignore
    public static final String ATTRIBUTE_COST = "cost";
    @Ignore
    public static final String TABLE_NAME = "shopping_session";


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ATTRIBUTE_ID)
    public long id;

    @ForeignKey(entity = Category.class, parentColumns = Category.ATTRIBUTE_ID, childColumns = ATTRIBUTE_CATEGORY_ID)
    @ColumnInfo(name = ATTRIBUTE_CATEGORY_ID)
    public long category_id;

    @ColumnInfo(name = ATTRIBUTE_TITLE)
    public String title;

    @ColumnInfo(name = ATTRIBUTE_COST)
    public double cost;

    public ShoppingSession() {

    }

    public ShoppingSession(ContentValues values) {
        id = values.getAsLong(ATTRIBUTE_ID);
        category_id = values.getAsLong(ATTRIBUTE_CATEGORY_ID);
        title = values.getAsString(ATTRIBUTE_TITLE);
        cost = values.getAsDouble(ATTRIBUTE_COST);
    }

    public ShoppingSession(long category_id, String title, double cost) {
        this.category_id = category_id;
        this.title = title;
        this.cost = cost;
    }

    public ShoppingSession(long id, long category_id, String title, double cost) {
        this(category_id, title, cost);
        this.id = id;
    }

    public ShoppingSession(Cursor mCursor) {
        this(
                mCursor.getLong(mCursor.getColumnIndex(ShoppingSession.ATTRIBUTE_ID)),
                mCursor.getLong(mCursor.getColumnIndex(ShoppingSession.ATTRIBUTE_CATEGORY_ID)),
                mCursor.getString(mCursor.getColumnIndex(ShoppingSession.ATTRIBUTE_TITLE)),
                mCursor.getDouble(mCursor.getColumnIndex(ShoppingSession.ATTRIBUTE_COST))
        );
    }

    public ContentValues getContentValues() {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ATTRIBUTE_ID, id);
        contentValues.put(ATTRIBUTE_CATEGORY_ID, category_id);
        contentValues.put(ATTRIBUTE_TITLE, title);
        contentValues.put(ATTRIBUTE_COST, cost);
        return contentValues;
    }

    public static ShoppingSession createShoppingSession(ContentValues values) {
        return new ShoppingSession(values);
    }
}
