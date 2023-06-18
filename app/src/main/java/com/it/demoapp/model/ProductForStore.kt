package com.it.demoproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.it.demoapp.model.DataConverter


@Entity(tableName = "ProductForStore")
data class ProductForStore(
    @ColumnInfo(name = "brand")
    var brand: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "discountPercentage")
    var discountPercentage: Double,

    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "images")
    var images: List<String>,

    @ColumnInfo(name = "price")
    var price: Int,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "stock")
    var stock: Int,

    @ColumnInfo(name = "thumbnail")
    var thumbnail: String,

    @ColumnInfo(name = "title")
    var title: String
)