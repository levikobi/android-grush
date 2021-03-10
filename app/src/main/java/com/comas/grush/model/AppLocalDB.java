package com.comas.grush.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.comas.grush.MyApplication;
import com.comas.grush.model.product.Product;
import com.comas.grush.model.product.ProductDao;

public class AppLocalDB {

    @Database(entities = {Product.class}, version = 20)
    public abstract static class AppLocalDBRepository extends RoomDatabase {
        public abstract ProductDao productDao();
    }

    public static AppLocalDBRepository db = Room
            .databaseBuilder(MyApplication.context, AppLocalDBRepository.class, "grushDB.db")
            .fallbackToDestructiveMigration()
            .build();
}
