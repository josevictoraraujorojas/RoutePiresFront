package com.example.routepiresfront.data.repository

import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.Localizacao
import com.example.routepiresfront.data.remote.ApiResponse
import com.example.routepiresfront.data.remote.ApiService
import retrofit2.Response
import java.io.IOException

/**
 * REPOSITORY - Camada que conversa com a API REST
 * ● Recebe chamadas do ViewModel
 * ● Retorna dados para o ViewModel (sucesso/erro)
 * ● Contém toda a lógica de obtenção dos dados da API
 * ● Isola a parte de rede do resto do app
 */
class CorridaRepository(private val apiService: ApiService) {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResponse.Success(body)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    ApiResponse.Success(Unit as T)
                }
            } else {
                ApiResponse.Error("Erro: ${response.code()} ${response.message()}", response.code())
            }
        } catch (e: IOException) {
            ApiResponse.Error("Erro de conexão com a internet. Verifique sua rede.")
        } catch (e: Exception) {
            ApiResponse.Error("Ocorreu um erro inesperado: ${e.message}")
        }
    }

    /**
     * Busca corridas disponíveis para o mototaxista aceitar
     * Usa endpoint de corridas de passageiro como base
     */
    suspend fun getCorridasDisponiveis(mototaxistaId: Long): ApiResponse<List<Corrida>> {
        return safeApiCall { apiService.getCorridasDisponiveis(mototaxistaId) }
    }

    /**
     * Aceita uma corrida (inicia negociação)
     * Atualiza status da corrida para aceita
     */
    suspend fun aceitarCorrida(corridaId: String, mototaxistaId: Long): ApiResponse<Corrida> {
        val body = mapOf(
            "mototaxistaId" to mototaxistaId,
            "status" to "ACEITA"
        )
        return safeApiCall { apiService.aceitarCorrida(corridaId, body) }
    }

    /**
     * Inicia a corrida (mototaxista começa a se deslocar)
     * Atualiza status da corrida para em andamento
     */
    suspend fun iniciarCorrida(corridaId: String): ApiResponse<Corrida> {
        return safeApiCall { apiService.iniciarCorrida(corridaId) }
    }

    /**
     * Atualiza localização do mototaxista durante a corrida
     */
    suspend fun atualizarLocalizacao(corridaId: String, localizacao: Localizacao): ApiResponse<Unit> {
        return safeApiCall { apiService.atualizarLocalizacao(corridaId, localizacao) }
    }

    /**
     * Finaliza a corrida
     * Atualiza status da corrida para finalizada
     */
    suspend fun finalizarCorrida(corridaId: String): ApiResponse<Corrida> {
        return safeApiCall { apiService.finalizarCorrida(corridaId) }
    }

    /**
     * Cancela a corrida
     * Atualiza status e adiciona motivo do cancelamento
     */
    suspend fun cancelarCorrida(corridaId: String, motivo: String?): ApiResponse<Corrida> {
        val body = motivo?.let { mapOf("motivo" to it, "status" to "CANCELADA") }
        return safeApiCall { apiService.cancelarCorrida(corridaId, body) }
    }
}


