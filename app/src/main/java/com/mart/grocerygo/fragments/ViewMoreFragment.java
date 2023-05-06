package com.mart.grocerygo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mart.grocerygo.adapters.GroceryHomeAdapter;
import com.mart.grocerygo.databinding.FragmentViewMoreBinding;
import com.mart.grocerygo.model.GroceryModel;

import java.util.ArrayList;
import java.util.List;

public class ViewMoreFragment extends Fragment {

    private FragmentViewMoreBinding binding;
    private List<GroceryModel> groceryList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewMoreBinding.inflate(inflater,container,false);

        if (getArguments() != null) {
            groceryList = (List<GroceryModel>) getArguments().getSerializable("grocery");
            setAdapter(groceryList);
        }



        return binding.getRoot();
    }

    private void setAdapter(List<GroceryModel> list) {
        GroceryHomeAdapter adapter;
        if (list.isEmpty()) {
            binding.noItemTv.setVisibility(View.VISIBLE);
        }else {
            binding.noItemTv.setVisibility(View.GONE);
            adapter = new GroceryHomeAdapter(requireActivity(),list,true);
            binding.RVViewMore.setLayoutManager(new GridLayoutManager(requireActivity(),2));
            binding.RVViewMore.setAdapter(adapter);
        }
}
}
