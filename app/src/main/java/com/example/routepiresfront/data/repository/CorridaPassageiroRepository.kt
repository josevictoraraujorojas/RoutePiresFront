package com.example.routepiresfront.data.repository

import com.example.routepiresfront.data.model.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.CorridaPassageiroResponse
import com.example.routepiresfront.data.model.Mototaxista
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
// Mantenha o código existente e adicione esta função dentro da classe:

    suspend fun buscarCorrida(id: String): Response<CorridaPassageiroResponse> {
        return try {
            apiService.buscarCorridaPorId(id)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun buscarMototaxista(id: String): Response<Mototaxista> {
        return apiService.buscarMototaxistaPorId(id)
    }

    suspend fun cancelarCorrida(id: String): Response<Void> {
        return apiService.cancelarCorrida(id)
    }
}