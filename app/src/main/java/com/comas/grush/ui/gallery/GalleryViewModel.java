package com.comas.grush.ui.gallery;

import androidx.lifecycle.LiveData;

import com.comas.grush.model.Model;
import com.comas.grush.model.product.Product;
import com.comas.grush.ui.product.ProductListViewModel;

import java.util.List;

public class GalleryViewModel extends ProductListViewModel {

    private LiveData<List<Product>> mProductList;

    public GalleryViewModel() {
        mProductList = Model.products.getAllByOwner();
    }

    public LiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public void refreshProductList() {
        mProductList = Model.products.getAllByOwner();
    }
}