package com.it.demoapp.api

import com.it.demoapp.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstance {

    private var INSTANCE: Retrofit? = null

    fun getInstance(): Retrofit = INSTANCE ?: kotlin.run {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
