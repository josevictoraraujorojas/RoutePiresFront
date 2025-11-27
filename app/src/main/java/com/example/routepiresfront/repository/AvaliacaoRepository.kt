package com.example.routepiresfront.repository


import br.gov.ifgoiano.routepiresfront.data.model.*

import br.gov.ifgoiano.routepiresfront.data.remote.AvaliacaoMototaxistaService
import br.gov.ifgoiano.routepiresfront.data.remote.AvaliacaoPassageiroService
import com.example.routepiresfront.data.model.AvaliacaoMototaxistaDTOCreate
import com.example.routepiresfront.data.model.AvaliacaoMototaxistaDTOResponse
import com.example.routepiresfront.data.model.AvaliacaoMototaxistaDTOUpdate
import com.example.routepiresfront.data.model.AvaliacaoPassageiroDTOCreate
import com.example.routepiresfront.data.model.AvaliacaoPassageiroDTOResponse
import com.example.routepiresfront.data.model.AvaliacaoPassageiroDTOUpdate
import com.example.routepiresfront.data.remote.ApiClient
import retrofit2.Response

class AvaliacaoRepository {

    private val mototaxistaService =
        ApiClient.getService(AvaliacaoMototaxistaService::class.java)

    private val passageiroService =
        ApiClient.getService(AvaliacaoPassageiroService::class.java)

    // ---------------------------------------------
    //  1) Criar avaliação
    // ---------------------------------------------
    suspend fun avaliarMototaxista(
        dto: AvaliacaoMototaxistaDTOCreate
    ): Result<AvaliacaoMototaxistaDTOResponse> {
        return safeCall { mototaxistaService.criarAvaliacao(dto) }
    }

    suspend fun avaliarPassageiro(
        dto: AvaliacaoPassageiroDTOCreate
    ): Result<AvaliacaoPassageiroDTOResponse> {
        return safeCall { passageiroService.criarAvaliacao(dto) }
    }


    // ---------------------------------------------
    //  2) Listagens e consultas
    // ---------------------------------------------
    suspend fun listarAvaliacoesMototaxista(mototaxistaId: String)
            : Result<List<AvaliacaoMototaxistaDTOResponse>> {
        return safeCall { mototaxistaService.listarPorMototaxista(mototaxistaId) }
    }

    suspend fun listarAvaliacoesPassageiro(avaliadoId: String)
            : Result<List<AvaliacaoPassageiroDTOResponse>> {
        return safeCall { passageiroService.listarPorAvaliado(avaliadoId) }
    }

    // ---------------------------------------------
    //  3) Atualizar
    // ---------------------------------------------
    suspend fun atualizarMototaxista(id: String, dto: AvaliacaoMototaxistaDTOUpdate)
            : Result<AvaliacaoMototaxistaDTOResponse> {
        return safeCall { mototaxistaService.atualizarAvaliacao(id, dto) }
    }

    suspend fun atualizarPassageiro(id: String, dto: AvaliacaoPassageiroDTOUpdate)
            : Result<AvaliacaoPassageiroDTOResponse> {
        return safeCall { passageiroService.atualizarAvaliacao(id, dto) }
    }

    // ---------------------------------------------
    //  4) Deletar (apenas passageiro tem delete)
    // ---------------------------------------------
    suspend fun deletarAvaliacaoPassageiro(id: String)
            : Result<Unit> {
        return safeCall {
            val response = passageiroService.deletarAvaliacao(id)
            if (response.isSuccessful) Response.success(Unit)
            else Response.error(response.code(), response.errorBody()!!)
        }
    }


    // ---------------------------------------------
    //  SAFE CALL → tratamento de erro padrão
    // ---------------------------------------------
    private inline fun <reified T> safeCall(
        call: () -> Response<T>
    ): Result<T> {
        return try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Resposta vazia do servidor."))
                }
            } else {
                Result.failure(Exception("Erro HTTP ${response.code()}: ${response.message()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
