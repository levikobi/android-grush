package com.comas.grush.model;

import android.os.AsyncTask;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public final static Model instance = new Model();

    private Model() { }

    public interface GetAllProductsListener {
        void onComplete(List<Product> products);
    }
    public void getAllProducts(GetAllProductsListener listener) {
        class MyAsyncTask extends AsyncTask {
            private List<Product> products;
            @Override
            protected Object doInBackground(Object[] objects) {
                products = AppLocalDB.db.productDao().getAll();
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(products);
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface GetProductByIdListener {
        void onComplete(Product product);
    }
    public void getProductById(Integer id, GetProductByIdListener listener) {
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
}
