package com.comas.grush.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comas.grush.R;
import com.comas.grush.ui.product.ProductListAdapter;
import com.comas.grush.model.Model;
import com.comas.grush.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private FloatingActionButton mAddProductFab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) root.getContext()).get(HomeViewModel.class);

        mRecyclerView = root.findViewById(R.id.recyclerview);
        mAddProductFab = root.findViewById(R.id.add_fab);


//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });

        mAdapter = new ProductListAdapter(root.getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        mViewModel.refreshProductList(() -> mAdapter.notifyDataSetChanged());

        mAddProductFab.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(HomeFragmentDirections.actionHomeToProductCreate()));

        return root;
    }
}