package com.example.assignment6.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.assignment6.data.room_items.Category;
import com.example.assignment6.data.room_items.CategoryDao;
import com.example.assignment6.data.room_items.ShoppingItem;
import com.example.assignment6.data.room_items.ShoppingItemDao;
import com.example.assignment6.data.room_items.ShoppingSession;
import com.example.assignment6.data.room_items.ShoppingSessionDao;

@Database(entities = {ShoppingSession.class, ShoppingItem.class, Category.class}, version = 1)
public abstract class ShoppingDatabase extends RoomDatabase {
    public abstract ShoppingSessionDao shoppingSessionDao();
    public abstract ShoppingItemDao shoppingItemDao();
    public abstract CategoryDao categoryDao();
}
