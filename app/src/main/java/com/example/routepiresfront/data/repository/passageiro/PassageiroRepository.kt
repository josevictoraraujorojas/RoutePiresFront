package com.example.routepiresfront.data.repository.passageiro

import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.passageiro.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.passageiro.CorridaPassageiroResponse
import com.example.routepiresfront.data.model.passageiro.MototaxistaPerfil
import com.example.routepiresfront.data.model.passageiro.MototaxistaResumo
import com.example.routepiresfront.data.model.passageiro.PassageiroCadastroRequest
import com.example.routepiresfront.data.model.passageiro.PassageiroResponse
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.ApiService
import retrofit2.Response

/**
 * Repositório central do fluxo de Passageiro.
 * Aqui deixamos as chamadas Retrofit encapsuladas para facilitar a troca de fonte de dados.
 */
class PassageiroRepository(
    private val api: ApiService = ApiClient.service
) {

    suspend fun cadastrarPassageiro(body: PassageiroCadastroRequest): Resultado<PassageiroResponse> =
        executarChamada { api.cadastrarPassageiro(body) }

    suspend fun buscarPassageiro(id: String): Resultado<PassageiroResponse> =
        executarChamada { api.buscarPassageiroPorId(id) }

    suspend fun atualizarPassageiro(id: String, body: PassageiroCadastroRequest): Resultado<PassageiroResponse> =
        executarChamada { api.atualizarPassageiro(id, body) }

    suspend fun historicoCorridas(id: String): Resultado<List<CorridaPassageiroResponse>> =
        executarChamada { api.historicoCorridas(id) }

    suspend fun listarMototaxistas(): Resultado<List<MototaxistaResumo>> =
        executarChamada { api.listarMototaxistas() }

    suspend fun perfilMototaxista(id: String): Resultado<MototaxistaPerfil> =
        executarChamada { api.perfilMototaxista(id) }

    suspend fun solicitarCorrida(body: CorridaPassageiroRequest): Resultado<CorridaPassageiroResponse> =
        executarChamada { api.solicitarCorridaPassageiro(body) }

    // Função helper genérica para reduzir boilerplate
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
                Resultado.Erro("Erro de API (HTTP ${resposta.code()}) ${erro.orEmpty()}")
            }
        } catch (e: Exception) {
            Resultado.Erro("Falha ao comunicar com o servidor: ${e.localizedMessage}", e)
        }
    }
}
