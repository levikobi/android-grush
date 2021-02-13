package com.comas.grush.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("select * from Product")
    LiveData<List<Product>> getAll();

    @Query("select * from Product where ownerId like :ownerId")
    LiveData<List<Product>> getAllByOwnerId(String ownerId);

    @Query("select * from Product where id like :id limit 1")
    Product getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Product... products);

    @Delete
    void delete(Product product);
}
