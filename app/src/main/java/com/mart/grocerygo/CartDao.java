package com.mart.grocerygo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mart.grocerygo.model.CartItem;

import java.util.List;

@Dao
public interface CartDao {

    @Query("Select * FROM CartItem")
    LiveData<List<CartItem>> getAllCartItem();

    @Insert
    void addCartItem(CartItem... item);

    @Delete
    void deleteCartItem(CartItem item);



}
