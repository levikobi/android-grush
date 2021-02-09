package com.comas.grush;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductFragment extends Fragment {

    private ProductViewModel mViewModel;

    private ImageView mProductImageView;
    private ImageButton mEditImageButton;
    private EditText mProductNameEditText;
    private EditText mProductDescEditText;
    private Button mSaveButton;
    private Button mEditButton;
    private Button mDeleteButton;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        initializeViewVariables(view);
        String productId = ProductFragmentArgs.fromBundle(getArguments()).getProductId();
        mProductNameEditText.setText(productId);
        return view;
    }

    private void initializeViewVariables(View view) {
        mProductImageView = view.findViewById(R.id.product_frag_image);
        mEditImageButton = view.findViewById(R.id.product_frag_edit_image);
        mProductNameEditText = view.findViewById(R.id.product_frag_name_text);
        mProductDescEditText = view.findViewById(R.id.product_frag_description_text);
        mSaveButton = view.findViewById(R.id.product_frag_save_button);
        mEditButton = view.findViewById(R.id.product_frag_edit_button);
        mDeleteButton = view.findViewById(R.id.product_frag_delete_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        // TODO: Use the ViewModel
    }

}