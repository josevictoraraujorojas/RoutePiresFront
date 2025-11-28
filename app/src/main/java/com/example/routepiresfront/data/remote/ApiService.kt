package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.CorridaPassageiroResponse
import com.example.routepiresfront.data.model.Mototaxista
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET // Adicionar import
import retrofit2.http.POST
import retrofit2.http.Path // Adicionar import

interface ApiService {
    @POST("corridas-passageiro")
    suspend fun criarCorridaPassageiro(@Body request: CorridaPassageiroRequest):
            Response<CorridaPassageiroResponse>

    // --- ADICIONE ISTO ABAIXO ---
    @GET("corridas-passageiro/{id}")
    suspend fun buscarCorridaPorId(@Path("id") id: String):
            Response<CorridaPassageiroResponse>

    @GET("mototaxistas/{id}")
    suspend fun buscarMototaxistaPorId(@Path("id") id: String): Response<Mototaxista>

    // Cancela a corrida (Muda status para CANCELADA)
    @POST("corridas-passageiro/{id}/cancelar")
    suspend fun cancelarCorrida(@Path("id") id: String): Response<Void>
}