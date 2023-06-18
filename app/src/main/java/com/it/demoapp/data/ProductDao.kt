package com.it.demoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.it.demoproject.model.ProductForStore


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProduct(productForStore: ProductForStore): Long

    @Query("SELECT * FROM ProductForStore")
    fun getAllProduct():List<ProductForStore>

    @Query("SELECT * FROM ProductForStore WHERE id LIKE :id")
    fun getProductAvailable(id: String): ProductForStore

    @Query("DELETE FROM ProductForStore WHERE id = :id")
    fun deleteById(id: String)

    @Update
    suspend fun updateUser(productForStore: ProductForStore): Int
}