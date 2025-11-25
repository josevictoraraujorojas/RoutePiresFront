package com.example.routepiresfront.data.remote

import retrofit2.Response
import com.example.routepiresfront.data.model.PassageiroResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface PassageiroService {
    @GET("passageiros/{id}")
    suspend fun getPassageiroById(@Path("id") id: String): Response<PassageiroResponseDTO>
}