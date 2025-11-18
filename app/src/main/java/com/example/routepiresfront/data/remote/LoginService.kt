package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.LoginDTOCreate
import com.example.routepiresfront.data.model.LoginDTOResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(
        @Body dto: LoginDTOCreate
    ): Response<LoginDTOResponse>
}