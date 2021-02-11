package com.comas.grush.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.comas.grush.model.Model;
import com.comas.grush.model.Product;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private LiveData<List<Product>> mProductList;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        Model.instance.getAllProducts(products -> mProductList = products);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public void refreshProductList(Runnable runnable) {
        Model.instance.getAllProducts(products -> {
            mProductList = products;
            runnable.run();
        });
    }
}