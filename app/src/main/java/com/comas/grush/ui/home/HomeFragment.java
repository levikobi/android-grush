package com.comas.grush.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.comas.grush.R;
import com.comas.grush.ui.product.ProductListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private FloatingActionButton mAddProductFab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) root.getContext()).get(HomeViewModel.class);

        mSwipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshProductList(() -> mAdapter.notifyDataSetChanged());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        mAddProductFab.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(HomeFragmentDirections.actionHomeToProductCreate()));

        return root;
    }
}