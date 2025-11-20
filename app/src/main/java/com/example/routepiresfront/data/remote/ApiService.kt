package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.CorridaPassageiroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("corridas-passageiro")
    suspend fun criarCorridaPassageiro(@Body request: CorridaPassageiroRequest):
            Response<CorridaPassageiroResponse>
}