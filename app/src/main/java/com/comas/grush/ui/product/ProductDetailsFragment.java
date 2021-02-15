package com.comas.grush.ui.product;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.comas.grush.model.Model;
import com.comas.grush.model.Product;
import com.comas.grush.ui.gallery.GalleryFragmentDirections;
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

        String productId = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProductId();
        boolean editable = ProductDetailsFragmentArgs.fromBundle(getArguments()).getEditable();

        setContainerVisibility(editable);

        Model.instance.getProductById(productId, product -> {
            mProduct = product;
            setContainerData();

            mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v)
                            .navigate(ProductDetailsFragmentDirections.actionProductDetailsToProductEdit(productId));
                }
            });
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.deleteProduct(mProduct, () -> Navigation.findNavController(view).popBackStack());
            }
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
}