package com.comas.grush.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public final static Model instance = new Model();

    private List<Product> products = new LinkedList<>();

    private Model() {
        products.add(new Product("0", "Couch", "Great couch indeed!", null));
        products.add(new Product("1", "Laptop", "As good as new", null));

    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(String id) {
        return products.get(Integer.parseInt(id));
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
