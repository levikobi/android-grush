package com.comas.grush.ui.product;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ProductFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_EXTERNAL_CONTENT = 1;

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
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mProductImageView.setImageBitmap(imageBitmap);
                break;
            case REQUEST_EXTERNAL_CONTENT:
                // TODO: fix uploading from gallery
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage == null) break;
                Cursor cursor = getActivity().getContentResolver()
                        .query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    mProductImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    cursor.close();
                }
                break;
        }
    }

    private void handleEditImage(View view) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your product's picture");

        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , REQUEST_EXTERNAL_CONTENT);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void handleSave(View view) {
        Product product = new Product();
        product.setName(mProductNameEditText.getText().toString());
        product.setDesc(mProductDescEditText.getText().toString());

        BitmapDrawable bitmapDrawable = (BitmapDrawable) mProductImageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Model.instance.uploadImage(bitmap, UUID.randomUUID().toString(), url -> {
            if (url == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Operation Failed");
                builder.setMessage("Saving image failed. Please try again later.");
                builder.setNeutralButton("OK", (dialog, which) -> dialog.dismiss());
                builder.show();
            } else {
                product.setImage(url);
                Model.instance.addProduct(product, () -> Navigation.findNavController(view).popBackStack());
            }
        });
    }
}