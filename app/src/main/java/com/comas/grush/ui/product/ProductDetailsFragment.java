package com.comas.grush.ui.product;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comas.grush.model.Model;
import com.comas.grush.model.Product;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProductDetailsFragment extends ProductFragment {

    private Product mProduct;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setContainerVisibility();

        String productId = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProductId();
        Model.instance.getProductById(productId, product -> {
            mProduct = product;
            setContainerData();
        });
        return view;
    }

    public void setContainerVisibility() {
        mEditImageButton.setVisibility(View.GONE);
        mDeleteButton.setVisibility(View.GONE);
        mEditButton.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.GONE);
        mProductNameEditText.setKeyListener(null);
        mProductNameEditText.setBackgroundResource(android.R.color.transparent);
        mProductDescEditText.setKeyListener(null);
        mProductDescEditText.setBackgroundResource(android.R.color.transparent);
    }

    private void setContainerData() {
        mProductNameEditText.setText(mProduct.getName());
        mProductDescEditText.setText(mProduct.getDesc());
        if (mProduct.getImage() != null) {
            Picasso.get().load(mProduct.getImage()).into(mProductImageView);
        }
    }
}