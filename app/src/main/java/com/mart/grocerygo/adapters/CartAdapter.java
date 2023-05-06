package com.mart.grocerygo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mart.grocerygo.R;
import com.mart.grocerygo.model.CartItem;
import com.mart.grocerygo.helpers.BitmapEncodingHelper;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {

        CartItem cartItem = cartItems.get(position);

        holder.itemName.setText(cartItem.getItemName());
        holder.itemQuantity.setText(String.valueOf(cartItem.getItemQuantity()));

        holder.itemPrice.setText(String.valueOf(cartItem.getTotalPrice()));
        holder.itemTotalPrice.setText(String.valueOf(cartItem.getItemQuantity() * cartItem.getTotalPrice()));

        holder.itemPriceCurrency.setText(cartItem.getCurrency());

        holder.itemImage.setImageBitmap(BitmapEncodingHelper.DecodeImage(cartItem.getItem_image()));



    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemName;
        private final TextView itemQuantity;
        private final TextView itemPrice;
        private final TextView itemTotalPrice;
        private final TextView itemPriceCurrency;

        private final ImageView itemImage;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tv_cart_item_name);
            itemQuantity = itemView.findViewById(R.id.tv_cart_item_quantity);
            itemPrice = itemView.findViewById(R.id.tv_cart_item_price);
            itemTotalPrice = itemView.findViewById(R.id.tv_total_cart_items_price);
            itemPriceCurrency = itemView.findViewById(R.id.tv_currency_cart_item);
            itemImage = itemView.findViewById(R.id.iv_cart_item_img);
        }
    }
}
