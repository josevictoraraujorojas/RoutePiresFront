package com.example.routepiresfront.data.repository.auth

import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.auth.LoginRequest
import com.example.routepiresfront.data.model.auth.UsuarioResponse
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.ApiService
import retrofit2.Response

/**
 * Autenticação simples contra o endpoint /login.
 */
class AuthRepository(
    private val api: ApiService = ApiClient.service
) {

    suspend fun login(body: LoginRequest): Resultado<UsuarioResponse> =
        executarChamada { api.login(body) }

    private suspend fun <T> executarChamada(chamada: suspend () -> Response<T>): Resultado<T> {
        return try {
            val resposta = chamada()
            if (resposta.isSuccessful) {
                val corpo = resposta.body()
                if (corpo != null) {
                    Resultado.Sucesso(corpo)
                } else {
                    Resultado.Erro("Resposta sem corpo (HTTP ${resposta.code()})")
                }
            } else {
                val erro = resposta.errorBody()?.string()
                val mensagemAmigavel = when (resposta.code()) {
                    400, 404 -> "Usuário ou senha inválidos"
                    else -> "Erro de API (HTTP ${resposta.code()}) ${erro.orEmpty()}"
                }
                Resultado.Erro(mensagemAmigavel)
            }
        } catch (e: Exception) {
            Resultado.Erro("Falha ao comunicar com o servidor: ${e.localizedMessage}", e)
        }
    }
}
