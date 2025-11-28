package br.gov.ifgoiano.routepiresfront.data.remote

import br.gov.ifgoiano.routepiresfront.data.model.DenunciaDTOCreate
import br.gov.ifgoiano.routepiresfront.data.model.DenunciaDTOResponse
import retrofit2.Response
import retrofit2.http.*

interface DenunciaService {

    @POST("denuncias")
    suspend fun criarDenuncia(
        @Body dto: DenunciaDTOCreate
    ): Response<DenunciaDTOResponse>

    @GET("denuncias/{id}")
    suspend fun buscarDenunciaPorId(
        @Path("id") id: String
    ): Response<DenunciaDTOResponse>

    @GET("denuncias")
    suspend fun listarTodasDenuncias(): Response<List<DenunciaDTOResponse>>

    @PUT("denuncias/{denunciaId}/validar")
    suspend fun validarDenuncia(
        @Path("denunciaId") denunciaId: String,
        @Query("adminId") adminId: String
    ): Response<DenunciaDTOResponse>

    @PUT("denuncias/{denunciaId}/rejeitar")
    suspend fun rejeitarDenuncia(
        @Path("denunciaId") denunciaId: String,
        @Query("adminId") adminId: String
    ): Response<DenunciaDTOResponse>
}
