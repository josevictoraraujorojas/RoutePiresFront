package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.MototaxistaCreate
import com.example.routepiresfront.data.model.MototaxistaUpdate
import com.example.routepiresfront.data.model.VeiculoCreate
import com.example.routepiresfront.data.model.VeiculoUpdate
import com.example.routepiresfront.data.remote.responses.MototaxistaResponse
import com.example.routepiresfront.data.remote.responses.VeiculoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MototaxistaApi {
    @POST("mototaxistas")
    suspend fun cadastrarMototaxista(@Body body: MototaxistaCreate): Response<MototaxistaResponse>

    @GET("mototaxistas")
    suspend fun listarTodos(): Response<List<MototaxistaResponse>>

    @GET("mototaxistas/{id}")
    suspend fun buscarPorId(@Path("id") id: String): Response<MototaxistaResponse>

    @PATCH("mototaxistas/{id}")
    suspend fun editar(
        @Path("id") id: String,
        @Body dados: MototaxistaUpdate
    ): Response<MototaxistaResponse>

    @GET("mototaxistas/{id}/veiculo")
    suspend fun getVeiculo(@Path("id") id: String): Response<VeiculoResponse>

    @PATCH("mototaxistas/{id}/veiculo")
    suspend fun atualizarVeiculo(
        @Path("id") id: String,
        @Body veiculo: VeiculoUpdate
    ): Response<VeiculoResponse>
}