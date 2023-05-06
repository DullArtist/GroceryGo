package com.mart.grocerygo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mart.grocerygo.model.CartItem;

@Database(entities = {CartItem.class},version = 1,exportSchema = false)
public abstract class GroceryDatabase extends RoomDatabase {

    public static GroceryDatabase Instance = null;

    public static GroceryDatabase getInstance(Context context) {

        if (Instance == null) {
            Instance = Room.databaseBuilder(context, GroceryDatabase.class,"GroceryDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return Instance;

    }

    public abstract CartDao cartDao();

}
