package com.example.routepiresfront.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Ajuste a BASE_URL para o seu backend
    private const val BASE_URL = "http://192.168.0.103:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> getService(clazz: Class<T>): T = retrofit.create(clazz)
}
