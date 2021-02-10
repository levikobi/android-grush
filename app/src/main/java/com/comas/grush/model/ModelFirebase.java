package com.comas.grush.model;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
}
