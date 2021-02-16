package com.comas.grush.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.comas.grush.MyApplication;
import com.comas.grush.R;
import com.comas.grush.ui.home.HomeFragmentDirections;
import com.comas.grush.ui.product.ProductListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class GalleryFragment extends Fragment {

    private GalleryViewModel mViewModel;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private FloatingActionButton mAddProductFab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) root.getContext()).get(GalleryViewModel.class);

        initializeViewElements(root);
        initializeRecyclerView(root);
        initializeViewHandlers();
        refreshProductList();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            displayLoginAlertDialog(root);
        }


        mViewModel.getProductList().observe(getViewLifecycleOwner(), products -> mAdapter.notifyDataSetChanged());

        return root;
    }

    private void initializeViewElements(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mRecyclerView = view.findViewById(R.id.gallery_frag_recyclerview);
        mAddProductFab = view.findViewById(R.id.add_fab);
    }

    private void initializeRecyclerView(View view) {
        mAdapter = new ProductListAdapter(getContext(), mViewModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initializeViewHandlers() {
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshProductList);
//        mAddProductFab.setOnClickListener(view -> Navigation.findNavController(view)
//                .navigate(HomeFragmentDirections.actionHomeToProductCreate()));
    }

    private void refreshProductList() {
        mViewModel.refreshProductList();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void displayLoginAlertDialog(View view) {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setCancelable(true);
        ad.setTitle("Hoops...");
        ad.setMessage("You need to be logged in to get in there!");
        ad.setButton("Login", (dialog, which) -> {
            dialog.dismiss();
            Navigation.findNavController(view).navigate(GalleryFragmentDirections.actionGalleryToSlideshow());
        });
        ad.setOnCancelListener(dialog -> {
            dialog.dismiss();
            Navigation.findNavController(view).popBackStack();
        });
        ad.show();
    }
}