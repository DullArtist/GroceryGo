package com.mart.grocerygo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mart.grocerygo.GroceryDatabase;
import com.mart.grocerygo.adapters.CartAdapter;
import com.mart.grocerygo.databinding.FragmentCartBinding;
import com.mart.grocerygo.helpers.CartItemHelper;
import com.mart.grocerygo.model.CartItem;

import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater,container,false);

        GroceryDatabase groceryDatabase = GroceryDatabase.getInstance(requireActivity());
        CartItemHelper cartItemHelper = new CartItemHelper(groceryDatabase);

        cartItemHelper.getAllCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartAdapter = new CartAdapter(cartItems);
            setAdapter(cartAdapter);
            setTotalPrice(cartItems);
        });


        return binding.getRoot();
    }

    private void setAdapter(CartAdapter cartAdapter) {
        binding.RVCart.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.RVCart.setAdapter(cartAdapter);
    }

    private void setTotalPrice(List<CartItem> cartItemList) {
        float grand_total_price = 0;
        for (int i=0;i<cartItemList.size();i++) {

            CartItem cartItem = cartItemList.get(i);
            grand_total_price += cartItem.getItemQuantity() * cartItem.getTotalPrice();

        }

        binding.tvTotalPrice.setText(String.valueOf(grand_total_price));
    }
}