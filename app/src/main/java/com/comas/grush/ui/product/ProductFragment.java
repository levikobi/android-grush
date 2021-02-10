package com.comas.grush.ui.product;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
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

import java.util.Objects;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ProductFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

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
        mEditImageButton.setOnClickListener(this::handleEditImage);
        mSaveButton.setOnClickListener(this::handleSave);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mProductImageView.setImageBitmap(imageBitmap);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void handleEditImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void handleSave(View view) {
        Product product = new Product();
        product.setName(mProductNameEditText.getText().toString());
        product.setDesc(mProductDescEditText.getText().toString());

        BitmapDrawable bitmapDrawable = (BitmapDrawable) mProductImageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Model.instance.uploadImage(bitmap, UUID.randomUUID().toString(), url -> {
            if (url == null) {

            } else {
                product.setImage(url);
                Model.instance.addProduct(product, () -> {
                    Navigation.findNavController(view).popBackStack();
                });
            }
        });
    }
}