package com.comas.grush.ui.home;

import android.os.Bundle;
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

        initializeViewElements(root);
        initializeRecyclerView(root);
        initializeViewHandlers();
        refreshProductList();

        mViewModel.getProductList().observe(getViewLifecycleOwner(), products -> mAdapter.notifyDataSetChanged());

        return root;
    }

    private void initializeViewElements(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mRecyclerView = view.findViewById(R.id.home_frag_recyclerview);
        mAddProductFab = view.findViewById(R.id.add_fab);
    }

    private void initializeRecyclerView(View view) {
        mAdapter = new ProductListAdapter(getContext(), mViewModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initializeViewHandlers() {
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshProductList);
        mAddProductFab.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(HomeFragmentDirections.actionHomeToProductCreate()));
    }

    private void refreshProductList() {
        mViewModel.refreshProductList();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}