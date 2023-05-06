package com.mart.grocerygo;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class GroceryApplication extends Application {

   public GroceryApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
