package br.gov.ifgoiano.routepiresfront.data.remote


import com.example.routepiresfront.data.model.AvaliacaoMototaxistaDTOCreate
import com.example.routepiresfront.data.model.AvaliacaoMototaxistaDTOResponse
import com.example.routepiresfront.data.model.AvaliacaoMototaxistaDTOUpdate
import retrofit2.Response
import retrofit2.http.*

interface AvaliacaoMototaxistaService {

    @POST("avaliacoes-mototaxista")
    suspend fun criarAvaliacao(
        @Body dto: AvaliacaoMototaxistaDTOCreate
    ): Response<AvaliacaoMototaxistaDTOResponse>

    @GET("avaliacoes-mototaxista/mototaxista/{mototaxistaId}")
    suspend fun listarPorMototaxista(
        @Path("mototaxistaId") mototaxistaId: String
    ): Response<List<AvaliacaoMototaxistaDTOResponse>>

    @GET("avaliacoes-mototaxista/{id}")
    suspend fun buscarPorId(
        @Path("id") id: String
    ): Response<AvaliacaoMototaxistaDTOResponse>

    @PUT("avaliacoes-mototaxista/{id}")
    suspend fun atualizarAvaliacao(
        @Path("id") id: String,
        @Body dto: AvaliacaoMototaxistaDTOUpdate
    ): Response<AvaliacaoMototaxistaDTOResponse>
}
