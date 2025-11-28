package com.example.routepiresfront.data.remote

import retrofit2.Response
import com.example.routepiresfront.data.model.MototaxistaResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MototaxistaService {
    @GET("mototaxistas/{id}")
    suspend fun getMototaxistaById(@Path("id") id: String): Response<MototaxistaResponseDTO>
}