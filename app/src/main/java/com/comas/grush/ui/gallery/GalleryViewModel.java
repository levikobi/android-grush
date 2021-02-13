package com.comas.grush.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.comas.grush.model.Model;
import com.comas.grush.model.Product;
import com.comas.grush.ui.product.ProductListViewModel;

import java.util.List;

public class GalleryViewModel extends ProductListViewModel {

    private LiveData<List<Product>> mProductList;

    public GalleryViewModel() {
        mProductList = Model.instance.getAllProductsByOwner();
    }

    public LiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public void refreshProductList() {
        mProductList = Model.instance.getAllProductsByOwner();
    }
}