package com.comas.grush.ui.home;

import androidx.lifecycle.LiveData;

import com.comas.grush.model.Model;
import com.comas.grush.model.product.Product;
import com.comas.grush.ui.product.ProductListViewModel;

import java.util.List;

public class HomeViewModel extends ProductListViewModel {

    private LiveData<List<Product>> mProductList;

    public HomeViewModel() {
        mProductList = Model.products.getAll();
    }

    public LiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public void refreshProductList() {
        mProductList = Model.products.getAll();
    }
}