package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.passageiro.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.passageiro.CorridaPassageiroResponse
import com.example.routepiresfront.data.model.passageiro.MototaxistaPerfil
import com.example.routepiresfront.data.model.passageiro.MototaxistaResumo
import com.example.routepiresfront.data.model.passageiro.PassageiroCadastroRequest
import com.example.routepiresfront.data.model.passageiro.PassageiroResponse
import com.example.routepiresfront.data.model.auth.LoginRequest
import com.example.routepiresfront.data.model.auth.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Endpoints disponibilizados pelo backend (vide Swagger).
 * Tudo em pt-BR e seguindo os DTOs do projeto Java.
 */
interface ApiService {

    // --- Login --- //
    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<UsuarioResponse>

    // --- Passageiro --- //
    @POST("passageiros")
    suspend fun cadastrarPassageiro(
        @Body body: PassageiroCadastroRequest
    ): Response<PassageiroResponse>

    @GET("passageiros/{id}")
    suspend fun buscarPassageiroPorId(
        @Path("id") id: String
    ): Response<PassageiroResponse>

    @PATCH("passageiros/{id}")
    suspend fun atualizarPassageiro(
        @Path("id") id: String,
        @Body body: PassageiroCadastroRequest
    ): Response<PassageiroResponse>

    @GET("passageiros/{id}/historico-corridas")
    suspend fun historicoCorridas(
        @Path("id") passageiroId: String
    ): Response<List<CorridaPassageiroResponse>>

    // --- Mototaxista --- //
    @GET("mototaxistas")
    suspend fun listarMototaxistas(): Response<List<MototaxistaResumo>>

    @GET("passageiros/mototaxista/{mototaxistaId}/perfil")
    suspend fun perfilMototaxista(
        @Path("mototaxistaId") mototaxistaId: String
    ): Response<MototaxistaPerfil>

    // --- Corridas do passageiro --- //
    @POST("corridas-passageiro")
    suspend fun solicitarCorridaPassageiro(
        @Body body: CorridaPassageiroRequest
    ): Response<CorridaPassageiroResponse>

    @GET("corridas-passageiro/{id}")
    suspend fun buscarCorridaPassageiro(
        @Path("id") id: String
    ): Response<CorridaPassageiroResponse>
}
