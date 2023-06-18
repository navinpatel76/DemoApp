package com.it.demoapp.data

import android.content.Context
import androidx.room.*
import com.it.demoapp.model.DataConverter
import com.it.demoproject.model.ProductForStore

@TypeConverters(DataConverter::class)
@Database(entities = [ProductForStore::class],version = 1,exportSchema = false)
abstract class ProductDataBase : RoomDatabase() {
    abstract fun ProductDao(): ProductDao
    companion object{
        @Volatile
        private var productDataBase:ProductDataBase?=null

        fun getDatabase(context: Context): ProductDataBase{
            val tempInstance= productDataBase
            if (tempInstance!=null){
                return   tempInstance

            }
            synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,
                    ProductDataBase::class.java,"ProductDataBase").build()
                productDataBase=instance
            }
            return productDataBase!!
        }
    }

}