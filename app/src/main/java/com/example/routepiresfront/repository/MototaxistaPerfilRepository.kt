package com.example.routepiresfront.repository

import android.util.Log
import com.example.routepiresfront.data.model.CorridaDTOResponse
import com.example.routepiresfront.data.model.MototaxistaDTOResponse
import com.example.routepiresfront.data.model.MototaxistaDTOUpdate
import com.example.routepiresfront.data.model.NotificacaoDTOResponse
import com.example.routepiresfront.data.model.VeiculoDTOResponse
import com.example.routepiresfront.data.model.VeiculoDTOUpdate
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.MototaxistaPerfilService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MototaxistaPerfilRepository {
    private val api: MototaxistaPerfilService = ApiClient.mototaxistaPerfilService()

    // ===== EDITAR PERFIL =====
    suspend fun updatePerfil(
        id: String,
        dto: MototaxistaDTOUpdate
    ): Result<MototaxistaDTOResponse> =
        withContext(Dispatchers.IO) {
            Log.d("MototaxistaRepo", "updatePerfil called for id=$id dto=$dto")
            safeCall { api.updatePerfil(id, dto) }
        }

    suspend fun getPerfilMototaxista(id: String): Result<MototaxistaDTOResponse> =
        withContext(Dispatchers.IO) {
            Log.d("MototaxistaRepo", "getPerfilMototaxista called for id=$id")
            safeCall { api.getPerfilMototaxista(id) }
        }

    // ===== VEÍCULO / PLACA =====
    suspend fun getVeiculo(id: String): Result<VeiculoDTOResponse> =
        withContext(Dispatchers.IO) {
            Log.d("MototaxistaRepo", "getVeiculo called for id=$id")
            safeCall { api.getVeiculo(id) }
        }

    suspend fun updateVeiculo(
        id: String,
        dto: VeiculoDTOUpdate
    ): Result<VeiculoDTOResponse> =
        withContext(Dispatchers.IO) {
            Log.d("MototaxistaRepo", "updateVeiculo called for id=$id dto=$dto")
            safeCall { api.updateVeiculo(id, dto) }
        }

    // ===== HISTÓRICO DE CORRIDAS =====
    suspend fun getHistoricoCorridas(id: String): Result<List<CorridaDTOResponse>> =
        withContext(Dispatchers.IO) {
            safeCall { api.getHistoricoCorridas(id) }
        }

    // ===== NOTIFICAÇÕES =====
    suspend fun getNotificacoes(id: String): Result<List<NotificacaoDTOResponse>> =
        withContext(Dispatchers.IO) {
            safeCall { api.getNotificacoes(id) }
        }

    suspend fun marcarNotificacaoComoLida(
        id: String,
        notificacaoId: String
    ): Result<NotificacaoDTOResponse> =
        withContext(Dispatchers.IO) {
            safeCall { api.marcarNotificacaoComoLida(id, notificacaoId) }
        }

    // ===== SAIR DA CONTA =====
    suspend fun logout(id: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            safeCall { api.logout(id) }
        }
    private suspend inline fun <T> safeCall(
        crossinline call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = runCatching { call() }.getOrElse { throw it }

            Log.d("MototaxistaRepo", "HTTP ${response.raw().request.method} ${response.raw().request.url} -> ${response.code()}")

            if (response.isSuccessful) {
                // Tratamento para 204 No Content
                if (response.code() == 204) {
                    @Suppress("UNCHECKED_CAST")
                    Log.d("MototaxistaRepo", "Resposta 204 No Content")
                    return Result.success(Unit as T)
                }

                val body = response.body()
                if (body != null) {
                    Log.d("MototaxistaRepo", "Resposta body: $body")
                    Result.success(body)
                } else {
                    Log.w("MototaxistaRepo", "Resposta com body nulo e código ${response.code()}")
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                val msg = "Erro HTTP ${response.code()}: ${response.message()}"
                // Tenta ler o corpo de erro para informações mais detalhadas
                val errorBodyStr = try { response.errorBody()?.string() } catch (e: Exception) { null }
                val fullMsg = if (!errorBodyStr.isNullOrBlank()) "$msg - $errorBodyStr" else msg
                Log.e("MototaxistaRepo", fullMsg)
                Result.failure(Exception(fullMsg))
            }
        } catch (e: Exception) {
            Log.e("MototaxistaRepo", "Exception durante chamada: ${e.message}", e)
            Result.failure(e)
        }
    }
}
