package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.CorridaDTOResponse
import com.example.routepiresfront.data.model.MototaxistaDTOResponse
import com.example.routepiresfront.data.model.NotificacaoDTOResponse
import com.example.routepiresfront.data.model.PassageiroResponseDTO
import com.example.routepiresfront.data.model.PassageiroUpdateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PassageiroPerfilService {
    // ===== EDITAR PERFIL =====
    @PUT("passageiros/{id}")
    suspend fun updatePerfil(
        @Path("id") id: String,
        @Body dto: PassageiroUpdateDTO
    ): Response<PassageiroResponseDTO>

    @GET("passageiros/{id}")
    suspend fun getPerfilPassageiro(
        @Path("id") id: String
    ): Response<PassageiroResponseDTO>  // ✅ Mudado para Profile

    // ===== HISTÓRICO DE CORRIDAS =====
    @GET("passageiros/{id}/historico-corridas")
    suspend fun getHistoricoCorridas(
        @Path("id") id: String
    ): Response<List<CorridaDTOResponse>>

    // ===== NOTIFICAÇÕES =====
    @GET("passageiros/{id}/notificacoes")
    suspend fun getNotificacoes(
        @Path("id") id: String
    ): Response<List<NotificacaoDTOResponse>>

    @PATCH("passageiros/{id}/notificacoes/{notificacaoId}/marcar-como-lida")
    suspend fun marcarNotificacaoComoLida(
        @Path("id") id: String,
        @Path("notificacaoId") notificacaoId: String
    ): Response<NotificacaoDTOResponse>

    // ===== SAIR DA CONTA (LOGOUT) =====
    @POST("passageiros/{id}/logout")
    suspend fun logout(
        @Path("id") id: String
    ): Response<Unit>

    // ===== VISUALIZAR PERFIL MOTOTAXISTA =====
    @GET("passageiros/mototaxista/{mototaxistaId}/perfil")
    suspend fun viewMototaxistaProfile(
        @Path("mototaxistaId") mototaxistaId: String
    ): Response<MototaxistaDTOResponse>
}