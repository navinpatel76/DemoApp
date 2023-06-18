package com.it.demoapp.viewModel.repositories

import android.app.Application
import android.util.Log
import com.it.demoapp.api.*
import com.it.demoapp.model.EmpolyeeList
import com.it.demoproject.model.ProductsModel


class EmployeeRepository private constructor(application: Application) {

    private val employeeCalls = RetroFitInstance.getInstance().create(EmployeeEndpoints::class.java)




    private lateinit var product : ProductsModel



    suspend fun getProductList() : ProductsModel {
        getAllProductFromRemote()
        return product
    }

    suspend fun getAllProductFromRemote() {
        try {
            Log.i(javaClass.name, "--=>")
            product = employeeCalls.getProductList()
            Log.i(javaClass.name, "--=>"+product.products)

        } catch (exception: Throwable) {
            exception.message
        }
    }

    companion object {

        private var INSTANCE: EmployeeRepository? = null

        fun getInstance(application: Application): EmployeeRepository = INSTANCE ?: kotlin.run {
            INSTANCE = EmployeeRepository(application = application)
            INSTANCE!!
        }
    }

}
