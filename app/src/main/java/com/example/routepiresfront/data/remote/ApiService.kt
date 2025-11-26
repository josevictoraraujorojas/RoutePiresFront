package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.Localizacao
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @GET("corridas-passageiro")
    suspend fun getCorridasPassageiro(): Response<List<Corrida>>

    @GET("corridas-passageiro/{id}")
    suspend fun getCorridaPassageiroById(
        @Path("id") corridaId: String
    ): Response<Corrida>

    @POST("corridas-passageiro")
    suspend fun createCorridaPassageiro(
        @Body corrida: Corrida
    ): Response<Corrida>

    @PUT("corridas-passageiro/{id}")
    suspend fun updateCorridaPassageiro(
        @Path("id") corridaId: String,
        @Body corrida: Corrida
    ): Response<Corrida>

    @DELETE("corridas-passageiro/{id}")
    suspend fun deleteCorridaPassageiro(
        @Path("id") corridaId: String
    ): Response<Void>

    @GET("corrida-frete")
    suspend fun getCorridasFrete(): Response<List<Corrida>>

    @GET("corrida-frete/{id}")
    suspend fun getCorridaFreteById(
        @Path("id") corridaId: String
    ): Response<Corrida>

    @POST("corrida-frete")
    suspend fun createCorridaFrete(
        @Body corrida: Corrida
    ): Response<Corrida>

    @PUT("corrida-frete/{id}")
    suspend fun updateCorridaFrete(
        @Path("id") corridaId: String,
        @Body corrida: Corrida
    ): Response<Corrida>

    @DELETE("corrida-frete/{id}")
    suspend fun deleteCorridaFrete(
        @Path("id") corridaId: String
    ): Response<Void>

    // ==================== ENDPOINTS PARA MOTOTAXISTA ====================
    // TODO: Estes endpoints precisam ser implementados no backend
    // Por enquanto, vamos usar os endpoints de passageiro/frete como base

    /**
     * Lista corridas disponíveis para o mototaxista aceitar
     * Endpoint temporário: GET /corridas-passageiro
     * TODO: Criar endpoint específico no backend: GET /corridas/disponiveis?mototaxistaId={id}
     */
    @GET("corridas-passageiro")
    suspend fun getCorridasDisponiveis(
        @Query("mototaxistaId") mototaxistaId: Long? = null
    ): Response<List<Corrida>>

    /**
     * Aceita uma corrida (inicia negociação)
     * TODO: Criar endpoint no backend: POST /corridas/{id}/aceitar
     * Por enquanto, usa update de corrida de passageiro
     */
    @PUT("corridas-passageiro/{id}")
    suspend fun aceitarCorrida(
        @Path("id") corridaId: String,
        @Body body: Map<String, Any>
    ): Response<Corrida>

    /**
     * Inicia a corrida (mototaxista começa a se deslocar)
     * TODO: Criar endpoint no backend: POST /corridas/{id}/iniciar
     * Por enquanto, usa update de corrida
     */
    @PUT("corridas-passageiro/{id}")
    suspend fun iniciarCorrida(
        @Path("id") corridaId: String
    ): Response<Corrida>

    /**
     * Atualiza localização do mototaxista durante a corrida
     * TODO: Criar endpoint no backend: PUT /corridas/{id}/localizacao
     */
    @PUT("corridas-passageiro/{id}")
    suspend fun atualizarLocalizacao(
        @Path("id") corridaId: String,
        @Body localizacao: Localizacao
    ): Response<Unit>

    /**
     * Finaliza a corrida
     * TODO: Criar endpoint no backend: POST /corridas/{id}/finalizar
     */
    @PUT("corridas-passageiro/{id}")
    suspend fun finalizarCorrida(
        @Path("id") corridaId: String
    ): Response<Corrida>

    /**
     * Cancela a corrida
     * TODO: Criar endpoint no backend: POST /corridas/{id}/cancelar
     */
    @PUT("corridas-passageiro/{id}")
    suspend fun cancelarCorrida(
        @Path("id") corridaId: String,
        @Body motivo: Map<String, String>? = null
    ): Response<Corrida>
}

