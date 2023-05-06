package com.mart.grocerygo.helpers;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mart.grocerygo.CartDao;
import com.mart.grocerygo.GroceryDatabase;
import com.mart.grocerygo.model.CartItem;

import java.util.List;

import javax.inject.Inject;

public class CartItemHelper {

    private final CartDao cartDao;

    @Inject
    public CartItemHelper(GroceryDatabase cartDatabase) {
        cartDao = cartDatabase.cartDao();

    }

    public LiveData<List<CartItem>> getAllCartItems(){
        return cartDao.getAllCartItem();
    }

    public void addCartItem(CartItem item) {
        new insertData(cartDao).execute(item);
    }


    public void deleteNote(CartItem cartItem) {
        new deleteData(cartDao).execute(cartItem);
    }


    private static class insertData extends AsyncTask<CartItem,Void,Void> {

        private final CartDao cartDao;

        public insertData(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartItem... cartItems) {
            cartDao.addCartItem(cartItems[0]);
            Log.i("LLL", "doInBackground: inserted");
            return null;
        }
    }


    private static class deleteData extends AsyncTask<CartItem,Void,Void> {

        private final CartDao cartDao;

        public deleteData(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartItem... cartItems) {
            cartDao.deleteCartItem(cartItems[0]);
            return null;
        }
    }

}


