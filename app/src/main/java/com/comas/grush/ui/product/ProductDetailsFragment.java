package com.comas.grush.ui.product;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.comas.grush.model.Model;
import com.comas.grush.model.product.Product;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProductDetailsFragment extends ProductFragment {

    private Product mProduct;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        String productId = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProductId();
        boolean editable = ProductDetailsFragmentArgs.fromBundle(getArguments()).getEditable();

        setContainerVisibility(editable);

        Model.products.getById(productId, product -> {
            runLoadingAnimation(false);
            mProduct = product;
            setContainerData();
            initializeViewHandlers();
        });

        return view;
    }

    private void setContainerVisibility(boolean editable) {
        mProductNameEditText.setKeyListener(null);
        mProductNameEditText.setBackgroundResource(android.R.color.transparent);
        mProductDescEditText.setKeyListener(null);
        mProductDescEditText.setBackgroundResource(android.R.color.transparent);
        mSaveButton.setVisibility(View.GONE);
        mEditImageButton.setVisibility(View.GONE);

        if (!editable) {
            mDeleteButton.setVisibility(View.GONE);
            mEditButton.setVisibility(View.GONE);
        }
    }

    private void setContainerData() {
        mProductNameEditText.setText(mProduct.getName());
        mProductDescEditText.setText(mProduct.getDesc());
        if (mProduct.getImage() != null) {
            Picasso.get().load(mProduct.getImage()).into(mProductImageView);
        }
    }

    private void initializeViewHandlers() {
        mEditButton.setOnClickListener(this::handleEdit);
        mDeleteButton.setOnClickListener(this::handleDelete);
    }

    private void handleEdit(View view) {
        Navigation.findNavController(view)
                .navigate(ProductDetailsFragmentDirections.actionProductDetailsToProductEdit(mProduct.getId()));
    }

    private void handleDelete(View view) {
        runLoadingAnimation(true);
        Model.products.delete(mProduct, () -> {
            runLoadingAnimation(false);
            Toast.makeText(getContext(), "Successfully deleted the product", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).popBackStack();
        });
    }
}