package com.example.routepiresfront.repository

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
                safeCall { api.updatePerfil(id, dto) }
            }

        suspend fun getPerfilPassageiro(id: String): Result<PassageiroResponseDTO> =  // ✅ Profile
            withContext(Dispatchers.IO) {
                safeCall { api.getPerfilPassageiro(id) }
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

            if (response.isSuccessful) {
                // Tratamento para 204 No Content
                if (response.code() == 204) {
                    @Suppress("UNCHECKED_CAST")
                    return Result.success(Unit as T)
                }

                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                val msg = "Erro HTTP ${response.code()}: ${response.message()}"
                Result.failure(Exception(msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
