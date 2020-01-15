package com.example.assignment6.data.room_items;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ShoppingSessionDao {
    @Query("SELECT * FROM "+ShoppingSession.TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM "+ShoppingSession.TABLE_NAME+" WHERE "+ShoppingSession.ATTRIBUTE_ID+"=:id")
    Cursor selectByID(long id);

    @Query("SELECT * FROM "+ShoppingSession.TABLE_NAME+" WHERE "+ShoppingSession.ATTRIBUTE_CATEGORY_ID+"=:category_id")
    Cursor selectByCategoryID(long category_id);

    @Query("SELECT SUM("+ShoppingSession.ATTRIBUTE_COST+") FROM "+ShoppingSession.TABLE_NAME)
    Cursor totalCost();

    @Insert
    long insert(ShoppingSession item);

    @Update
    int update(ShoppingSession item);

    @Query("DELETE FROM "+ShoppingSession.TABLE_NAME+" WHERE "+ShoppingSession.ATTRIBUTE_ID+"= :id")
    int deleteUsingID(long id);
}
