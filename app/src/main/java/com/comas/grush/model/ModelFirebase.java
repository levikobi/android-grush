package com.comas.grush.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ModelFirebase {

    private static final String COLLECTION_PATH = "products";

    public void getAllProducts(Model.GetAllProductsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_PATH).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("TAG", "Get all products SUCCESS");
                    List<Product> products = queryDocumentSnapshots.toObjects(Product.class);
                    listener.onComplete(products);
                });
    }

    public void getProductById(String id, Model.GetProductByIdListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_PATH).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Log.d("TAG", "Get a product SUCCESS");
                    Product product = documentSnapshot.toObject(Product.class);
                    listener.onComplete(product);
                });
    }

    public void addProduct(Product product, Model.AddProductListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newProductRef = db.collection(COLLECTION_PATH).document();
        product.setId(newProductRef.getId());
        newProductRef.set(product)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Add a product SUCCESS");
                    listener.onComplete();
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Add a product FAILURE");
                    listener.onComplete();
                });
    }

    public void uploadImage(Bitmap imageBmp, String name, Model.UploadImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });

    }
}
