package com.example.routepiresfront.repository

import android.util.Log
import com.example.routepiresfront.data.model.CorridaDTOResponse
import com.example.routepiresfront.data.model.MototaxistaDTOResponse
import com.example.routepiresfront.data.model.NotificacaoDTOResponse
import com.example.routepiresfront.data.model.PassageiroResponseDTO
import com.example.routepiresfront.data.model.PassageiroUpdateDTO
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.PassageiroPerfilService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PassageiroPerfilRepository {
        private val api: PassageiroPerfilService = ApiClient.passageiroPerfilService()

        // ===== EDITAR PERFIL =====
        suspend fun updatePerfil(
            id: String,
            dto: PassageiroUpdateDTO
        ): Result<PassageiroResponseDTO> =
            withContext(Dispatchers.IO) {
                Log.d("PassageiroRepo", "updatePerfil called for id=$id dto=$dto")
                safeCall { api.updatePerfil(id, dto) }
            }

        suspend fun getPerfilPassageiro(id: String): Result<PassageiroResponseDTO> =  // ✅ Profile
            withContext(Dispatchers.IO) {
                Log.d("PassageiroRepo", "getPerfilPassageiro called for id=$id")
                safeCall { api.getPerfilPassageiro(id) }
            }

        // ===== HISTÓRICO DE CORRIDAS =====
        suspend fun getHistoricoCorridas(id: String): Result<List<CorridaDTOResponse>> =
            withContext(Dispatchers.IO) {
                try {
                    Log.d("PassageiroRepo", "getHistoricoCorridas direct call for id=$id")
                    val response = api.getHistoricoCorridas(id)
                    Log.d("PassageiroRepo", "HTTP ${response.raw().request.method} ${response.raw().request.url} -> ${response.code()}")

                    if (response.isSuccessful) {
                        if (response.code() == 204) {
                            Log.d("PassageiroRepo", "Historico vazio (204)")
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
                        Log.e("PassageiroRepo", fullMsg)
                        return@withContext Result.failure(Exception(fullMsg))
                    }
                } catch (e: Exception) {
                    Log.e("PassageiroRepo", "Exception durante getHistoricoCorridas: ${e.message}", e)
                    Result.failure(e)
                }
            }

        // ===== NOTIFICAÇÕES =====
        suspend fun getNotificacoes(id: String): Result<List<NotificacaoDTOResponse>> =
            withContext(Dispatchers.IO) {
                Log.d("PassageiroRepo", "getNotificacoes called for id=$id")
                safeCall { api.getNotificacoes(id) }
            }

        suspend fun marcarNotificacaoComoLida(
            id: String,
            notificacaoId: String
        ): Result<NotificacaoDTOResponse> =
            withContext(Dispatchers.IO) {
                Log.d("PassageiroRepo", "marcarNotificacaoComoLida called for id=$id notificacaoId=$notificacaoId")
                safeCall { api.marcarNotificacaoComoLida(id, notificacaoId) }
            }

        // ===== SAIR DA CONTA =====
        suspend fun logout(id: String): Result<Unit> =
            withContext(Dispatchers.IO) {
                Log.d("PassageiroRepo", "logout called for id=$id")
                safeCall { api.logout(id) }
            }

        // ===== VISUALIZAR PERFIL MOTOTAXISTA =====
        suspend fun viewMototaxistaProfile(
            mototaxistaId: String
        ): Result<MototaxistaDTOResponse> =
            withContext(Dispatchers.IO) {
                safeCall { api.viewMototaxistaProfile(mototaxistaId) }
            }

    private suspend inline fun <T> safeCall(
        crossinline call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = runCatching { call() }.getOrElse { throw it }

            Log.d("PassageiroRepo", "HTTP ${response.raw().request.method} ${response.raw().request.url} -> ${response.code()}")

            if (response.isSuccessful) {
                if (response.code() == 204) {
                    // No Content: retorna null para que o chamador possa decidir o que fazer
                    Log.d("PassageiroRepo", "Resposta 204 No Content")
                    @Suppress("UNCHECKED_CAST")
                    return Result.success(null as T)
                }

                val body = response.body()
                if (body != null) {
                    Log.d("PassageiroRepo", "Resposta body: $body")
                    Result.success(body)
                } else {
                    Log.w("PassageiroRepo", "Resposta com body nulo e código ${response.code()}")
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                val msg = "Erro HTTP ${response.code()}: ${response.message()}"
                val errorBodyStr = try { response.errorBody()?.string() } catch (e: Exception) { null }
                val fullMsg = if (!errorBodyStr.isNullOrBlank()) "$msg - $errorBodyStr" else msg
                Log.e("PassageiroRepo", fullMsg)
                Result.failure(Exception(fullMsg))
            }
        } catch (e: Exception) {
            Log.e("PassageiroRepo", "Exception durante chamada: ${e.message}", e)
            Result.failure(e)
        }
     }
 }
