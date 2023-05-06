package com.mart.grocerygo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mart.grocerygo.databinding.FragmentFavouritesBinding;

public class FavouritesFragment extends Fragment {

    private FragmentFavouritesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater,container,false);




        return binding.getRoot();
    }
}