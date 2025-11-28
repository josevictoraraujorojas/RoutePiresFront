package com.example.routepiresfront.data.repository

import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.mapper.CorridaMapper
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.ApiResponse
import com.example.routepiresfront.data.remote.CorridaService
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * REPOSITORY - Camada que conversa com a API REST
 * ● Recebe chamadas do ViewModel
 * ● Retorna dados para o ViewModel (sucesso/erro)
 * ● Contém toda a lógica de obtenção dos dados da API
 * ● Isola a parte de rede do resto do app
 * ● Usa DTOs e Mapper para compatibilidade com backend
 */
class CorridaRepository {

    private val corridaService: CorridaService = ApiClient.getService(CorridaService::class.java)

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


    suspend fun getCorridasDisponiveis(): ApiResponse<List<Corrida>> {
        return safeApiCall { corridaService.getCorridasDisponiveis() }
    }

    suspend fun aceitarCorrida(corridaId: String, mototaxistaId: String): ApiResponse<Corrida> {
        val dto = CorridaMapper.toAceitarCorridaDTO(mototaxistaId)
        return safeApiCall { corridaService.atualizarCorrida(corridaId, dto) }
    }

    suspend fun iniciarCorrida(corridaId: String): ApiResponse<Corrida> {
        val dataHora = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
        val dto = CorridaMapper.toIniciarCorridaDTO(dataHora)
        return safeApiCall { corridaService.atualizarCorrida(corridaId, dto) }
    }

    suspend fun finalizarCorrida(corridaId: String): ApiResponse<Corrida> {
        val dataHora = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
        val dto = CorridaMapper.toFinalizarCorridaDTO(dataHora)
        return safeApiCall { corridaService.atualizarCorrida(corridaId, dto) }
    }

    suspend fun cancelarCorrida(corridaId: String, motivo: String?): ApiResponse<Corrida> {
        val dto = CorridaMapper.toCancelarCorridaDTO(motivo)
        return safeApiCall { corridaService.atualizarCorrida(corridaId, dto) }
    }
}

