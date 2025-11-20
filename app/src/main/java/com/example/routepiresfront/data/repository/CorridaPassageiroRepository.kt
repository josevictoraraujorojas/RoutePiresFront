package com.example.routepiresfront.data.repository

import com.example.routepiresfront.data.model.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.CorridaPassageiroResponse
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.ApiService
import retrofit2.Response

class CorridaPassageiroRepository(
    private val apiService: ApiService = ApiClient.instance.create(ApiService::class.java)
) {
    // Metodo que o ViewModel chamará
    suspend fun criarCorrida(request: CorridaPassageiroRequest): Response<CorridaPassageiroResponse> {
        // A lógica de obtenção dos dados da API está isolada aqui.
        return try {
            apiService.criarCorridaPassageiro(request)
        } catch (e: Exception) {
            throw e
        }
    }
}