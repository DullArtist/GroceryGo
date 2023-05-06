package com.mart.grocerygo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mart.grocerygo.R;
import com.mart.grocerygo.adapters.GroceryHomeAdapter;
import com.mart.grocerygo.databinding.FragmentHomeBinding;
import com.mart.grocerygo.model.GroceryModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference databaseGroceryReference;

    private final List<GroceryModel> FruitList = new ArrayList<>();
    private final List<GroceryModel> VegetableList = new ArrayList<>();
    private final List<GroceryModel> MeatList = new ArrayList<>();
    private final List<GroceryModel> BakeryList = new ArrayList<>();
    private final List<GroceryModel> BeveragesList = new ArrayList<>();
    private final List<GroceryModel> LentilsList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        databaseGroceryReference = FirebaseDatabase.getInstance().getReference("My_Grocery_Shop");
        databaseGroceryReference.keepSynced(true);

        readData();

        binding.tvViewMore.setOnClickListener(view -> filterViewMore(binding.chipGroup.getCheckedChipId()));

        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {

            for (int i = 0; i < group.getChildCount(); i++) {
                Chip chip = (Chip) group.getChildAt(i);
                chip.setOnClickListener(view -> {
                    if (chip.isChecked()) {
                        filterGrocery(chip.getId());
                    } else {
                        // Chip is unchecked
                    }
                });

            }
    });

        binding.chipFruit.setOnClickListener(view -> NavHostFragment.findNavController(this).navigate(R.id.itemFragment));

        return binding.getRoot();
    }

    private void readData() {

        databaseGroceryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                FruitList.clear();
                VegetableList.clear();
                BakeryList.clear();
                MeatList.clear();
                LentilsList.clear();
                BeveragesList.clear();

                if (snapshot.hasChildren()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        GroceryModel grocery_item = dataSnapshot.getValue(GroceryModel.class);

                        switch (Objects.requireNonNull(grocery_item).getGrocery_Category()) {
                            case "Fruits":
                                FruitList.add(grocery_item);
                                break;
                            case "Vegetables":
                                VegetableList.add(grocery_item);
                                break;
                            case "Bakery":
                                BakeryList.add(grocery_item);
                                break;
                            case "Beverages":
                                BeveragesList.add(grocery_item);
                                break;
                            case "Meat":
                                MeatList.add(grocery_item);
                                break;
                            case "Lentils":
                                LentilsList.add(grocery_item);
                                break;
                        }

                    }


//                    setAdapter(FruitList);
                    filterGrocery(binding.chipGroup.getCheckedChipId());

                }
                binding.progressCircularHome.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                binding.progressCircularHome.setVisibility(View.GONE);
            }
        });


    }

    private void filterViewMore(int id) {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        Bundle arguments = new Bundle();

        if (id == R.id.chip_fruit) {
            arguments.putSerializable("grocery", (Serializable) FruitList);
            navController.navigate(R.id.viewMoreFragment, arguments);
        } else if (id == R.id.chip_vegetable) {
            arguments.putSerializable("grocery", (Serializable) VegetableList);
            navController.navigate(R.id.viewMoreFragment, arguments);

        } else if (id == R.id.chip_bakery) {
            arguments.putSerializable("grocery", (Serializable) BakeryList);
            navController.navigate(R.id.viewMoreFragment, arguments);

        } else if (id == R.id.chip_meat) {
            arguments.putSerializable("grocery", (Serializable) MeatList);
            navController.navigate(R.id.viewMoreFragment, arguments);

        } else if (id == R.id.chip_legumes_lentils) {
            arguments.putSerializable("grocery", (Serializable) LentilsList);
            navController.navigate(R.id.viewMoreFragment, arguments);

        } else if (id == R.id.chip_beverages) {
            arguments.putSerializable("grocery", (Serializable) BeveragesList);
            navController.navigate(R.id.viewMoreFragment, arguments);
        }

    }

    private void filterGrocery(int id) {

        if (id == R.id.chip_fruit) {
            setAdapter(FruitList);

        } else if (id == R.id.chip_vegetable) {
            setAdapter(VegetableList);

        } else if (id == R.id.chip_bakery) {
            setAdapter(BakeryList);

        } else if (id == R.id.chip_meat) {
            setAdapter(MeatList);

        } else if (id == R.id.chip_legumes_lentils) {
            setAdapter(LentilsList);

        } else if (id == R.id.chip_beverages) {
            setAdapter(BeveragesList);
        }
    }

    private void setAdapter(List<GroceryModel> list) {
        GroceryHomeAdapter adapter;
        if (list.isEmpty()) {
            binding.tvNoItem.setVisibility(View.VISIBLE);
            binding.RVGroceries.setAdapter(null);
        }else {
            binding.tvNoItem.setVisibility(View.GONE);
            adapter = new GroceryHomeAdapter(requireActivity(),list,false);
            binding.RVGroceries.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false));
            binding.RVGroceries.setAdapter(adapter);
        }

    }
}