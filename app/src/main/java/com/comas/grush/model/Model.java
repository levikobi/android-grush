package com.comas.grush.model;

import java.util.List;

public class Model {

    public final static Model instance = new Model();

    private final ModelFirebase modelFirebase = new ModelFirebase();
    private final ModelRoom modelRoom = new ModelRoom();

    private Model() { }

    public interface GetAllProductsListener {
        void onComplete(List<Product> products);
    }
    public void getAllProducts(GetAllProductsListener listener) {
        modelFirebase.getAllProducts(listener);
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
}
