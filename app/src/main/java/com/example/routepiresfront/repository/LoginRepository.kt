package com.example.routepiresfront.repository

import retrofit2.Response
import com.example.routepiresfront.data.model.LoginDTO
import com.example.routepiresfront.data.model.UsuarioDTOResponse
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {
    private val api: LoginService = ApiClient.loginService()

    suspend fun login(dto: LoginDTO): Result<UsuarioDTOResponse> = withContext(Dispatchers.IO) {
        safeCall { api.login(dto) }
    }

    private suspend fun <T> safeCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = runCatching { call() }.getOrElse { throw it }

            if (response.isSuccessful) {

                // --- Tratamento correto para 204 No Content ---
                if (response.code() == 204) {
                    // Retorna null para manter o comportamento consistente com outros repositórios
                    @Suppress("UNCHECKED_CAST")
                    return@safeCall Result.success(null as T)
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