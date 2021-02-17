package com.comas.grush.model.product;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAll();

    @Query("SELECT * FROM Product " +
           "WHERE ownerId LIKE :ownerId")
    LiveData<List<Product>> getAllByOwnerId(String ownerId);

    @Query("SELECT * FROM Product " +
           "WHERE id LIKE :id " +
           "LIMIT 1")
    Product getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Product... products);

    @Delete
    void delete(Product product);
}
