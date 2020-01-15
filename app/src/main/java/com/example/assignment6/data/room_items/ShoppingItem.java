package com.example.assignment6.data.room_items;


import android.content.ContentValues;
import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = ShoppingItem.TABLE_NAME)
public class ShoppingItem {
    @Ignore
    public static final String TABLE_NAME = "shopping_item";
    @Ignore
    public static final String ATTRIBUTE_ID = "id";
    @Ignore
    public static final String ATTRIBUTE_PARENT_ID = "parent_id";
    @Ignore
    public static final String ATTRUBUTE_TITLE = "title";
    @Ignore
    public static final String ATTRUBUTE_COST = "cost";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ATTRIBUTE_ID)
    public long id;

    @ForeignKey(entity = ShoppingSession.class, parentColumns = ShoppingSession.ATTRIBUTE_ID, childColumns = ATTRIBUTE_PARENT_ID)
    @ColumnInfo(name = ATTRIBUTE_PARENT_ID)
    public long parent_id;

    @ColumnInfo(name = ATTRUBUTE_TITLE)
    public String title;

    @ColumnInfo(name = ATTRUBUTE_COST)
    public double cost;

    public ShoppingItem() {
    }

    public ShoppingItem(long parent_id, String title, double cost) {
        this.parent_id = parent_id;
        this.title = title;
        this.cost = cost;
    }

    public ShoppingItem(ContentValues contentValues) {
        this.id = contentValues.getAsLong(ATTRIBUTE_ID);
        this.parent_id = contentValues.getAsLong(ATTRIBUTE_PARENT_ID);
        this.title = contentValues.getAsString(ATTRUBUTE_TITLE);
        this.cost = contentValues.getAsDouble(ATTRUBUTE_COST);
    }

    public ShoppingItem(long id, long parent_id, String title, double cost) {
        this(parent_id, title, cost);
        this.id = id;
    }

    public ShoppingItem(Cursor mCursor) {
        this(
            mCursor.getLong(mCursor.getColumnIndex(ShoppingItem.ATTRIBUTE_ID)),
            mCursor.getLong(mCursor.getColumnIndex(ShoppingItem.ATTRIBUTE_PARENT_ID)),
            mCursor.getString(mCursor.getColumnIndex(ShoppingItem.ATTRUBUTE_TITLE)),
            mCursor.getDouble(mCursor.getColumnIndex(ShoppingItem.ATTRUBUTE_COST))
        );
    }

    public ContentValues getContentValues() {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ATTRIBUTE_ID, this.id);
        contentValues.put(ATTRIBUTE_PARENT_ID, this.parent_id);
        contentValues.put(ATTRUBUTE_TITLE, this.title);
        contentValues.put(ATTRUBUTE_COST, this.cost);
        return contentValues;
    }

}
