package br.gov.ifgoiano.routepiresfront.data.remote


import com.example.routepiresfront.data.model.AvaliacaoPassageiroDTOCreate
import com.example.routepiresfront.data.model.AvaliacaoPassageiroDTOResponse
import com.example.routepiresfront.data.model.AvaliacaoPassageiroDTOUpdate
import retrofit2.Response
import retrofit2.http.*

interface AvaliacaoPassageiroService {

    @POST("avaliacoes-passageiro")
    suspend fun criarAvaliacao(
        @Body dto: AvaliacaoPassageiroDTOCreate
    ): Response<AvaliacaoPassageiroDTOResponse>

    @GET("avaliacoes-passageiro/avaliado/{avaliadoId}")
    suspend fun listarPorAvaliado(
        @Path("avaliadoId") avaliadoId: String
    ): Response<List<AvaliacaoPassageiroDTOResponse>>

    @GET("avaliacoes-passageiro/{id}")
    suspend fun buscarPorId(
        @Path("id") id: String
    ): Response<AvaliacaoPassageiroDTOResponse>

    @PATCH("avaliacoes-passageiro/{id}")
    suspend fun atualizarAvaliacao(
        @Path("id") id: String,
        @Body dto: AvaliacaoPassageiroDTOUpdate
    ): Response<AvaliacaoPassageiroDTOResponse>

    @DELETE("avaliacoes-passageiro/{id}")
    suspend fun deletarAvaliacao(
        @Path("id") id: String
    ): Response<Void>
}
