package com.comas.grush.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comas.grush.R;
import com.comas.grush.ui.product.ProductListAdapter;
import com.comas.grush.model.Model;
import com.comas.grush.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;

    private List<Product> mProductList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

//        mProductList = Model.instance.getAllProducts();
        Model.instance.getAllProducts(new Model.GetAllProductsListener() {
            @Override
            public void onComplete(List<Product> products) {
                mProductList = products;

                // Get a handle to the RecyclerView.
                mRecyclerView = root.findViewById(R.id.recyclerview);
                // Create an adapter and supply the data to be displayed.
                mAdapter = new ProductListAdapter(root.getContext(), mProductList);
                // Connect the adapter with the RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                // Give the RecyclerView a default layout manager.
                mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                FloatingActionButton fab = root.findViewById(R.id.add_fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeToProductCreate());
                    }
                });

                for (Product product : mProductList) {
                    Log.d("TAG", String.valueOf(product.getId()));
                }
            }
        });



        return root;
    }
}