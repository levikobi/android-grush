package com.comas.grush.model;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ModelRoom {
    public interface GetAllProductsListener {
        void onComplete(List<Product> products);
    }

    public LiveData<List<Product>> getAllProducts() {
        return AppLocalDB.db.productDao().getAll();
    }

    public LiveData<List<Product>> getAllProductsByOwnerId(String ownerId) {
        return AppLocalDB.db.productDao().getAllByOwnerId(ownerId);
    }

    public interface GetProductByIdListener {
        void onComplete(Product product);
    }
    public void getProductById(String id, GetProductByIdListener listener) {
        class MyAsyncTask extends AsyncTask {
            private Product product;
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.d("TAG", String.valueOf(id));
                product = AppLocalDB.db.productDao().getById(id);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(product);
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface AddProductListener {
        void onComplete();
    }
    public void addProduct(Product product, AddProductListener listener) {
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.productDao().insertAll(product);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null) listener.onComplete();
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface DeleteProductListener {
        void onComplete();
    }
    public void deleteProduct(Product product, DeleteProductListener listener) {
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.productDao().delete(product);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null) listener.onComplete();
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}
