package com.comas.grush.model.product;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.comas.grush.model.AppLocalDB;

import java.util.List;

public class ModelRoom {

    public LiveData<List<Product>> getAll() {
        return AppLocalDB.db.productDao().getAll();
    }

    public LiveData<List<Product>> getAllByOwnerId(String ownerId) {
        return AppLocalDB.db.productDao().getAllByOwnerId(ownerId);
    }

    public void getById(String id, ProductModel.GetByIdListener listener) {
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

    public interface AddListener {
        void onComplete();
    }
    public void add(Product product, AddListener listener) {
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

    public interface DeleteListener {
        void onComplete();
    }
    public void delete(Product product, DeleteListener listener) {
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
