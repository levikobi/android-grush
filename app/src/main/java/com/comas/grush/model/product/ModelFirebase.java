 package com.comas.grush.model.product;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ModelFirebase {

    private static final String COLLECTION_PATH = "products";

    public interface GetAllListener {
        void onComplete(List<Product> products);
    }
    public void getAll(Long lastUpdated, GetAllListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp timestamp = new Timestamp(lastUpdated);
        List<Product> products = new LinkedList<>();
        db.collection(COLLECTION_PATH)
                .whereGreaterThanOrEqualTo("lastUpdated", timestamp)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("TAG", "***Getting products from Firebase***");
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Product product = new Product();
                        product.fromMap(doc.getData());
                        products.add(product);
                        Log.d("TAG", product.getName());
                    }
                    listener.onComplete(products);
                })
                .addOnFailureListener(e -> listener.onComplete(products));
    }

    public void getById(String id, ProductModel.GetByIdListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_PATH).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Log.d("TAG", "Get a product SUCCESS");
                    Product product = new Product();
                    product.fromMap(Objects.requireNonNull(documentSnapshot.getData()));
                    listener.onComplete(product);
                });
    }

    public void add(Product product, ProductModel.AddListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newProductRef = db.collection(COLLECTION_PATH).document();
        product.setId(newProductRef.getId());
        product.setRemoved(false);
        product.setOwnerId(FirebaseAuth.getInstance().getUid());
        newProductRef.set(product.toMap())
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Add a product SUCCESS");
                    listener.onComplete();
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Add a product FAILURE");
                    listener.onComplete();
                });
    }

    public void uploadImage(Bitmap imageBmp, String name, ProductModel.UploadImageListener listener) {
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

    public void delete(Product product, ProductModel.DeleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_PATH).document(product.getId()).update("isRemoved", true)
                .addOnSuccessListener(aVoid -> {
                    db.collection(COLLECTION_PATH).document(product.getId()).update("lastUpdated", FieldValue.serverTimestamp())
                            .addOnSuccessListener(a -> {
                                if (listener != null) listener.onComplete();
                            });
                });

    }
}
