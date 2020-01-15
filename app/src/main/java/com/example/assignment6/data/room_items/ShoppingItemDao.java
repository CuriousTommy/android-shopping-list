package com.example.assignment6.data.room_items;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ShoppingItemDao {
    @Query("SELECT * FROM "+ShoppingItem.TABLE_NAME+" WHERE "+ShoppingItem.ATTRIBUTE_PARENT_ID+"=:parent_id")
    Cursor selectSection(long parent_id);

    @Query("SELECT SUM("+ShoppingItem.ATTRUBUTE_COST+") FROM "+ShoppingItem.TABLE_NAME+" WHERE "+ShoppingItem.ATTRIBUTE_PARENT_ID+"=:parent_id")
    Cursor totalCostFromSection(long parent_id);

    @Insert
    long insert(ShoppingItem item);

    @Update
    int update(ShoppingItem item);

    @Query("DELETE FROM "+ShoppingItem.TABLE_NAME+" WHERE "+ShoppingItem.ATTRIBUTE_ID+"=:id")
    int deleteUsingID(long id);

    @Query("DELETE FROM "+ShoppingItem.TABLE_NAME+" WHERE "+ShoppingItem.ATTRIBUTE_PARENT_ID+"=:parent_id")
    int deleteSectionUsingParentID(long parent_id);
}
