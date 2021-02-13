package com.comas.grush.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.comas.grush.MyApplication;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Model {

    public final static Model instance = new Model();

    private final ModelFirebase modelFirebase = new ModelFirebase();
    private final ModelRoom modelRoom = new ModelRoom();

    private LiveData<List<Product>> products;
    private LiveData<List<Product>> productsByOwner;

    private Model() { }

    public LiveData<List<Product>> getAllProducts() {
        if (products == null) {
            products = modelRoom.getAllProducts();
        }
        fetchUpdatedDataFromFirebase();
        return products;
    }

    public LiveData<List<Product>> getAllProductsByOwner() {
        if (productsByOwner == null) {
            String ownerId = FirebaseAuth.getInstance().getUid();
            productsByOwner = modelRoom.getAllProductsByOwnerId(ownerId);
        }
        fetchUpdatedDataFromFirebase();
        return productsByOwner;
    }

    private void fetchUpdatedDataFromFirebase() {
        SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sharedPreferences.getLong("lastUpdated", 0);

        modelFirebase.getAllProducts(lastUpdated, new ModelFirebase.GetAllProductsListener() {
            @Override
            public void onComplete(List<Product> result) {
                long lastU = 0;
                for (Product product : result) {
                    modelRoom.addProduct(product, null);
                    if (product.getLastUpdated() > lastU) {
                        lastU = product.getLastUpdated();
                    }
                }
                sharedPreferences.edit().putLong("lastUpdated", lastU).apply();
            }
        });
    }

    public interface GetProductByIdListener {
        void onComplete(Product product);
    }
    public void getProductById(String id, GetProductByIdListener listener) {
        modelFirebase.getProductById(id, listener);
    }

    public interface AddProductListener {
        void onComplete();
    }
    public void addProduct(Product product, AddProductListener listener) {
        modelFirebase.addProduct(product, listener);
    }

    public interface UploadImageListener {
        void onComplete(String url);
    }
    public void uploadImage(Bitmap imageBmp, String name, UploadImageListener listener) {
        modelFirebase.uploadImage(imageBmp, name, listener);
    }
}
