package com.example.routepiresfront.data.remote

import br.gov.ifgoiano.routepiresfront.data.model.MensagemDTOUpdate
import br.gov.ifgoiano.routepiresfront.data.model.MensagenDTOCreate
import com.example.routepiresfront.data.model.MensagemDTOResponse
import retrofit2.Response
import retrofit2.http.*

interface MensagemService {

    @POST("mensagem")
    suspend fun createMensagem(
        @Body dto: MensagenDTOCreate
    ): Response<MensagemDTOResponse>

    @PUT("mensagem/{id}")
    suspend fun updateMensagem(
        @Path("id") id: String,
        @Body dto: MensagemDTOUpdate
    ): Response<MensagemDTOResponse>

    @GET("mensagem")
    suspend fun getAllMensagens(): Response<List<MensagemDTOResponse>>

    @GET("mensagem/{id}")
    suspend fun getMensagemById(
        @Path("id") id: String
    ): Response<MensagemDTOResponse>

    @DELETE("mensagem/{id}")
    suspend fun deleteMensagem(
        @Path("id") id: String
    ): Response<Unit> // 204 NO CONTENT
}
