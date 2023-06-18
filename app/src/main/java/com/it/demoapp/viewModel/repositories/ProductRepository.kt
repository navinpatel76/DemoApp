package com.example.roomdatabase.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.it.demoapp.data.ProductDao
import com.it.demoproject.model.ProductForStore

class ProductRepository (private val userDao: ProductDao){
//    val getAllProduct : List<ProductForStore> = userDao.getAllProduct()

    suspend fun getAllProduct(): List<ProductForStore>{
        return userDao.getAllProduct()
    }
    suspend fun addProduct(productForStore: ProductForStore): Long{
        return userDao.addProduct(productForStore)
    }



    suspend fun getProduct(id: String):ProductForStore?{
        return userDao.getProductAvailable(id)
    }

    suspend fun deleteById(id: String){
        return userDao.deleteById(id)
    }

//    suspend fun updateUser(user: User): Int{
//        return userDao.updateUser(user)
//    }
}