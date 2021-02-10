package com.comas.grush.model;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ModelFirebase {

    public void getAllProducts(Model.GetAllProductsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").get()
                .addOnCompleteListener(task -> {
                    List<Product> products = new LinkedList<>();
                    if (task.isSuccessful()) {
                        Log.d("TAG", "Get all products SUCCESS");
                        for (DocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                            Product product = doc.toObject(Product.class);
                            products.add(product);
                        }
                    }
                    listener.onComplete(products);
                });
    }

    public void getProductById(String id, Model.GetProductByIdListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").document(id).get()
                .addOnCompleteListener(task -> {
                    Product product = null;
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        product = doc.toObject(Product.class);
                    }
                    listener.onComplete(product);
                });
    }

    public void addProduct(Product product, Model.AddProductListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newProductRef = db.collection("products").document();
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
