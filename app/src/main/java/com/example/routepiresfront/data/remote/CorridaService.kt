package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.dto.CorridaPassageiroUpdateDTO
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface Retrofit para requisições de corridas
 * Baseada nos endpoints do backend:
 * - CorridaPassageiroController: /corridas-passageiro
 * - CorridaFreteController: /corrida-frete
 */
interface CorridaService {

    /**
     * Lista todas as corridas disponíveis (status AGUARDANDO)
     * Endpoint: GET /corridas-passageiro
     */
    @GET("corridas-passageiro")
    suspend fun getCorridasDisponiveis(): Response<List<Corrida>>

    /**
     * Busca uma corrida específica por ID
     * Endpoint: GET /corridas-passageiro/{id}
     */
    @GET("corridas-passageiro/{id}")
    suspend fun getCorridaById(@Path("id") corridaId: String): Response<Corrida>

    /**
     * Atualiza uma corrida (aceitar, iniciar, finalizar, cancelar)
     * Endpoint: PUT /corridas-passageiro/{id}
     */
    @PUT("corridas-passageiro/{id}")
    suspend fun atualizarCorrida(
        @Path("id") corridaId: String,
        @Body dto: CorridaPassageiroUpdateDTO
    ): Response<Corrida>

    /**
     * Lista todas as corridas de frete disponíveis
     * Endpoint: GET /corrida-frete
     */
    @GET("corrida-frete")
    suspend fun getCorridasFreteDisponiveis(): Response<List<Corrida>>

    /**
     * Busca uma corrida de frete específica por ID
     * Endpoint: GET /corrida-frete/{id}
     */
    @GET("corrida-frete/{id}")
    suspend fun getCorridaFreteById(@Path("id") corridaId: String): Response<Corrida>

    /**
     * Atualiza uma corrida de frete
     * Endpoint: PUT /corrida-frete/{id}
     */
    @PUT("corrida-frete/{id}")
    suspend fun atualizarCorridaFrete(
        @Path("id") corridaId: String,
        @Body dto: CorridaPassageiroUpdateDTO
    ): Response<Corrida>
}

