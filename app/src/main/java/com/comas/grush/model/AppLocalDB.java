package com.comas.grush.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.comas.grush.MyApplication;

@Database(entities = {Product.class}, version = 10)
abstract class AppLocalDBRepository extends RoomDatabase {
    public abstract ProductDao productDao();
}

public class AppLocalDB {
    public static AppLocalDBRepository db = Room
            .databaseBuilder(MyApplication.context, AppLocalDBRepository.class, "grushDB.db")
            .fallbackToDestructiveMigration()
            .build();
}
