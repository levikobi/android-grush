package com.comas.grush.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.comas.grush.model.product.Product;

import java.util.List;

public abstract class ProductListViewModel extends ViewModel {
    public abstract LiveData<List<Product>> getProductList();
}
