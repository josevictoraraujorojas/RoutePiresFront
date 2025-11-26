package com.example.routepiresfront.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton para fornecer uma instância do Retrofit e do ApiService.
 */
object RetrofitClient {

    // TODO: Substitua pela URL base da sua API real
    private const val BASE_URL = "https://api.example.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
