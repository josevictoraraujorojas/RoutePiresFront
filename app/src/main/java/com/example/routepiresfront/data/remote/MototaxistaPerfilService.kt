package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.CorridaDTOResponse
import com.example.routepiresfront.data.model.MototaxistaDTOResponse
import com.example.routepiresfront.data.model.MototaxistaDTOUpdate
import com.example.routepiresfront.data.model.NotificacaoDTOResponse
import com.example.routepiresfront.data.model.VeiculoDTOResponse
import com.example.routepiresfront.data.model.VeiculoDTOUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MototaxistaPerfilService {

    // ===== EDITAR PERFIL =====
    @PATCH("mototaxistas/{id}")
    suspend fun updatePerfil(
        @Path("id") id: String,
        @Body dto: MototaxistaDTOUpdate
    ): Response<MototaxistaDTOResponse>

    @GET("mototaxistas/{id}")
    suspend fun getPerfilMototaxista(
        @Path("id") id: String
    ): Response<MototaxistaDTOResponse>

    // ===== VEÍCULO / PLACA =====
    @GET("mototaxistas/{id}/veiculo")
    suspend fun getVeiculo(
        @Path("id") id: String
    ): Response<VeiculoDTOResponse>

    @PUT("mototaxistas/{id}/veiculo")
    suspend fun updateVeiculo(
        @Path("id") id: String,
        @Body dto: VeiculoDTOUpdate
    ): Response<VeiculoDTOResponse>

    // ===== HISTÓRICO DE CORRIDAS =====
    @GET("mototaxistas/{id}/historico-corridas")
    suspend fun getHistoricoCorridas(
        @Path("id") id: String
    ): Response<List<CorridaDTOResponse>>

    // ===== NOTIFICAÇÕES =====
    @GET("mototaxistas/{id}/notificacoes")
    suspend fun getNotificacoes(
        @Path("id") id: String
    ): Response<List<NotificacaoDTOResponse>>

    @PATCH("mototaxistas/{id}/notificacoes/{notificacaoId}/marcar-como-lida")
    suspend fun marcarNotificacaoComoLida(
        @Path("id") id: String,
        @Path("notificacaoId") notificacaoId: String
    ): Response<NotificacaoDTOResponse>

    // LOGOUT - Não deleta, apenas encerra a sessão
    @POST("mototaxistas/{id}/logout")
    suspend fun logout(
        @Path("id") id: String
    ): Response<Unit>
}