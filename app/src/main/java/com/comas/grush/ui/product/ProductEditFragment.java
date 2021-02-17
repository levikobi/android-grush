package com.comas.grush.ui.product;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.comas.grush.model.Model;
import com.comas.grush.model.product.Product;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class ProductEditFragment extends ProductFragment {

    private Product mProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        String productId = ProductEditFragmentArgs.fromBundle(getArguments()).getProductId();

        setContainerVisibility();

        Model.products.getById(productId, product -> {
            runLoadingAnimation(false);
            mProduct = product;
            setContainerData();
            initializeViewHandlers();
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

    private void initializeViewHandlers() {
        mSaveButton.setOnClickListener(this::handleSave);
    }

    private void handleSave(View view) {
        runLoadingAnimation(true);
        // 1. Delete current product.
        // 2. Create a new product from the new data.
        Product newProduct = new Product();
        newProduct.setName(mProductNameEditText.getText().toString());
        newProduct.setDesc(mProductDescEditText.getText().toString());

        BitmapDrawable bitmapDrawable = (BitmapDrawable) mProductImageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Model.products.uploadImage(bitmap, UUID.randomUUID().toString(), url -> {

            if (url == null) {
                runLoadingAnimation(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Operation Failed");
                builder.setMessage("Saving image failed. Please try again later.");
                builder.setNeutralButton("OK", (dialog, which) -> dialog.dismiss());
                builder.show();
            } else {
                newProduct.setImage(url);
                Model.products.edit(mProduct, newProduct, () -> {
                    runLoadingAnimation(false);
                    Toast.makeText(getContext(), "Successfully edited your product", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(ProductEditFragmentDirections.actionProductEditToGallery());
                });
            }
        });
    }
}