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
            try {
                Log.d("MototaxistaRepo", "getHistoricoCorridas direct call for id=$id")
                val response = api.getHistoricoCorridas(id)
                Log.d("MototaxistaRepo", "HTTP ${response.raw().request.method} ${response.raw().request.url} -> ${response.code()}")

                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        Log.d("MototaxistaRepo", "Historico vazio (204)")
                        return@withContext Result.success(emptyList())
                    }

                    val body = response.body()
                    return@withContext if (body != null) {
                        Result.success(body)
                    } else {
                        Result.failure(Exception("Resposta vazia ao obter histórico de corridas"))
                    }
                } else {
                    val msg = "Erro HTTP ${response.code()}: ${response.message()}"
                    val errorBodyStr = try { response.errorBody()?.string() } catch (e: Exception) { null }
                    val fullMsg = if (!errorBodyStr.isNullOrBlank()) "$msg - $errorBodyStr" else msg
                    Log.e("MototaxistaRepo", fullMsg)
                    Log.e("MototaxistaRepo", "Request URL: ${response.raw().request.url}")

                    // Se o endpoint não existe (404) — provavelmente a API ainda não implementou —
                    // tratamos como histórico vazio para não quebrar a UI.
                    if (response.code() == 404) {
                        Log.w("MototaxistaRepo", "Endpoint de histórico de corridas para mototaxista não encontrado (404), retornando lista vazia. Error body: $errorBodyStr")
                        return@withContext Result.success(emptyList())
                    }

                    return@withContext Result.failure(Exception(fullMsg))
                }
            } catch (e: Exception) {
                Log.e("MototaxistaRepo", "Exception durante getHistoricoCorridas: ${e.message}", e)
                Result.failure(e)
            }
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
            val response = call()

            Log.d("MototaxistaRepo", "HTTP ${response.raw().request.method} ${response.raw().request.url} -> ${response.code()}")

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("MototaxistaRepo", "Resposta com corpo: $body")
                    Result.success(body)
                } else if (response.code() == 204) {
                    Log.d("MototaxistaRepo", "Resposta 204 No Content, retornando sucesso com corpo nulo.")
                    @Suppress("UNCHECKED_CAST")
                    Result.success(null as T)
                } else {
                    val msg = "Resposta com código ${response.code()} mas corpo nulo ou malformado."
                    Log.e("MototaxistaRepo", msg)
                    Result.failure(Exception(msg))
                }
            } else {
                val errorBodyStr = try {
                    response.errorBody()?.string()?.take(500)
                } catch (e: Exception) {
                    "Falha ao ler o corpo do erro: ${e.message}"
                }
                val errorMsg = "Erro HTTP ${response.code()}: ${response.message()}. Detalhes: $errorBodyStr"
                Log.e("MototaxistaRepo", errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("MototaxistaRepo", "Exceção na chamada de rede: ${e.message}", e)
            Result.failure(Exception("Falha na comunicação com o servidor. Verifique a conexão. Detalhes: ${e.message}", e))
        }
    }
}