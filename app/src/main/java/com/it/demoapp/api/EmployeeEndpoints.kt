package com.it.demoapp.api

import com.it.demoapp.model.EmpolyeeList
import com.it.demoproject.model.ProductsModel
import retrofit2.http.GET

interface EmployeeEndpoints {

    // Simple get request to get a list of all employees
    @GET("employees")
    suspend fun getEmployeeList(): EmpolyeeList

    @GET("products")
    suspend fun getProductList(): ProductsModel

}
