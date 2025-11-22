package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.LoginDTO
import com.example.routepiresfront.data.model.UsuarioDTOResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(
        @Body dto: LoginDTO
    ): Response<UsuarioDTOResponse>
}