package com.mart.grocerygo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String itemName;
    private String itemCategory;
    private int itemQuantity;
    private float totalPrice;
    private String currency;
    private String item_image;

    public CartItem(String itemName, String itemCategory, int itemQuantity, float totalPrice,String currency,String item_image) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.item_image = item_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }
}
