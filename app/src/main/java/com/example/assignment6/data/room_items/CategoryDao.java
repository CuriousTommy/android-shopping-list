package com.example.assignment6.data.room_items;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM "+Category.TABLE_NAME)
    Cursor selectAll();

    @Insert
    long insert(Category item);

    @Update
    int update(Category item);

    @Query("DELETE FROM "+Category.TABLE_NAME+" WHERE "+Category.ATTRIBUTE_ID+"=:id")
    int deleteUsingID(long id);
}
