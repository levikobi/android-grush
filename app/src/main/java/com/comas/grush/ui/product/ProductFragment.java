package com.comas.grush.ui.product;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.comas.grush.R;
import com.comas.grush.model.Model;
import com.comas.grush.model.Product;

public class ProductFragment extends Fragment {

    private ProductViewModel mViewModel;

    protected ImageView mProductImageView;
    protected ImageButton mEditImageButton;
    protected EditText mProductNameEditText;
    protected EditText mProductDescEditText;
    protected Button mSaveButton;
    protected Button mEditButton;
    protected Button mDeleteButton;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        initializeViewElements(view);
        initializeViewHandlers();
        return view;
    }

    private void initializeViewElements(View view) {
        mProductImageView = view.findViewById(R.id.product_frag_image);
        mEditImageButton = view.findViewById(R.id.product_frag_edit_image);
        mProductNameEditText = view.findViewById(R.id.product_frag_name_text);
        mProductDescEditText = view.findViewById(R.id.product_frag_description_text);
        mSaveButton = view.findViewById(R.id.product_frag_save_button);
        mEditButton = view.findViewById(R.id.product_frag_edit_button);
        mDeleteButton = view.findViewById(R.id.product_frag_delete_button);
    }

    private void initializeViewHandlers() {
        mSaveButton.setOnClickListener(this::handleSave);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        // TODO: Use the ViewModel
    }

    private void handleSave(View view) {
        Model.instance.addProduct(new Product("2",
                mProductNameEditText.getText().toString(),
                mProductDescEditText.getText().toString(),
                null));
        Navigation.findNavController(view).popBackStack();
    }
}