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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setContainerVisibility();

        String productId = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProductId();
//        mProduct = Model.instance.getProductById(productId);
        Model.instance.getProductById(productId, new Model.GetProductByIdListener() {
            @Override
            public void onComplete(Product product) {
                mProduct = product;
                setContainerData();
            }
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