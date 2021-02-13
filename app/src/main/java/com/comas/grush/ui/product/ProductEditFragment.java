package com.comas.grush.ui.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comas.grush.R;
import com.comas.grush.model.Model;
import com.comas.grush.model.Product;
import com.squareup.picasso.Picasso;

public class ProductEditFragment extends ProductFragment {

    private Product mProduct;

    public ProductEditFragment() {
        // Required empty public constructor
    }

    public static ProductEditFragment newInstance() {
        ProductEditFragment fragment = new ProductEditFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        String productId = ProductEditFragmentArgs.fromBundle(getArguments()).getProductId();

        setContainerVisibility();

        Model.instance.getProductById(productId, product -> {
            mProduct = product;
            setContainerData();
        });
        return view;
    }

    private void setContainerVisibility() {
        mDeleteButton.setVisibility(View.GONE);
        mEditButton.setVisibility(View.GONE);
    }

    private void setContainerData() {
        mProductNameEditText.setText(mProduct.getName());
        mProductDescEditText.setText(mProduct.getDesc());
        if (mProduct.getImage() != null) {
            Picasso.get().load(mProduct.getImage()).into(mProductImageView);
        }
    }
}