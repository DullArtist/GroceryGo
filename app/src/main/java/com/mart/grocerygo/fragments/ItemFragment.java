package com.mart.grocerygo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mart.grocerygo.GroceryDatabase;
import com.mart.grocerygo.R;
import com.mart.grocerygo.databinding.FragmentItemBinding;
import com.mart.grocerygo.helpers.BitmapEncodingHelper;
import com.mart.grocerygo.helpers.CartItemHelper;
import com.mart.grocerygo.model.CartItem;
import com.mart.grocerygo.model.GroceryModel;

public class ItemFragment extends Fragment implements View.OnClickListener {

    private int counter = 1;
    private FragmentItemBinding binding;
    private GroceryModel groceryModel;

    private CartItemHelper cartItemHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemBinding.inflate(inflater,container,false);

        if (getArguments() != null) {
            groceryModel = (GroceryModel) getArguments().getSerializable("grocery");
        }

        binding.llFoodImg.setElevation(20f);

        binding.tvSelectedQuantity.setText(String.valueOf(counter));

        GroceryDatabase cartDatabase = GroceryDatabase.getInstance(requireActivity());
        cartItemHelper = new CartItemHelper(cartDatabase);

        setTotalPrice();

        setValues(groceryModel);

        binding.ivDec.setOnClickListener(this);
        binding.ivInc.setOnClickListener(this);
        binding.ivFavIc.setOnClickListener(this);

        binding.llBtnAddCart.setOnClickListener(view -> {

            String itemName = groceryModel.getGrocery_Name();
            String itemCategory = groceryModel.getGrocery_Category();
            int itemQuantity = Integer.parseInt(binding.tvSelectedQuantity.getText().toString().trim());
            float total_price = Float.parseFloat(binding.tvTotalPrice.getText().toString());

            String itemCurrency = groceryModel.getPrice_Currency();
            String itemImage = groceryModel.getGrocery_Image();

            addToCart(new CartItem(itemName,itemCategory,itemQuantity,total_price,itemCurrency,itemImage));
        });




        return binding.getRoot();
    }
    

    private void addToCart(CartItem cartItem) {
        cartItemHelper.addCartItem(cartItem);
        Toast.makeText(requireActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
    }

    private void setValues(GroceryModel groceryItem) {

        binding.tvGroceryFoodItem.setText(groceryItem.getGrocery_Name());
        binding.tvGroceryFoodItemPrice.setText(String.valueOf(groceryItem.getGrocery_Price()));
        binding.tvTotalPrice.setText(String.valueOf(groceryItem.getGrocery_Price()));

        binding.tvGroceryFoodItemQuantity.setText(String.valueOf(groceryItem.getGrocery_Quantity()));
        binding.tvSelectedQuantity.setText(String.valueOf(counter));

        binding.tvFoodItemDescription.setText(groceryItem.getGrocery_Description());

        binding.tvSelectedQuantityUnit.setText(groceryItem.getQuantity_Unit());
        binding.tvGroceryFoodItemUnit.setText(groceryItem.getQuantity_Unit());

        binding.tvGroceryFoodItemCurrency.setText(groceryItem.getPrice_Currency());

        binding.ivGroceryFoodItem.setImageBitmap(BitmapEncodingHelper.DecodeImage(groceryItem.getGrocery_Image()));


    }

    private void setTotalPrice() {
        int price = Integer.parseInt(binding.tvGroceryFoodItemPrice.getText().toString()) * Integer.parseInt(binding.tvSelectedQuantity.getText().toString());
        binding.tvTotalPrice.setText(String.valueOf(price));
    }

    public void incrementCounter() {
        counter++; // Increment counter by 1
        binding.tvSelectedQuantity.setText(String.valueOf(counter));
        setTotalPrice();
    }

    public void decrementCounter() {
        if (counter > 1) {
            counter--; // Decrement counter by 1
            binding.tvSelectedQuantity.setText(String.valueOf(counter));
            setTotalPrice();
        }
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_dec) {
            decrementCounter();
        }else if (view.getId() == R.id.iv_inc) {
            incrementCounter();
        }
    }


}