package com.comas.grush.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.comas.grush.model.Model;
import com.comas.grush.model.Product;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private LiveData<List<Product>> mProductList;

    public HomeViewModel() {
        mProductList = Model.instance.getAllProducts();
    }

    public LiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public void refreshProductList() {
        mProductList = Model.instance.getAllProducts();
    }
}