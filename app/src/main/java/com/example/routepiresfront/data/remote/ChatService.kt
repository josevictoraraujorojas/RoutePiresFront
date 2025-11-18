package com.example.routepiresfront.data.remote

import com.example.routepiresfront.data.model.ChatDTOCreate
import com.example.routepiresfront.data.model.ChatDTOResponse
import com.example.routepiresfront.data.model.ChatDTOUpdate
import com.example.routepiresfront.data.model.MensagemDTOResponse
import retrofit2.Response
import retrofit2.http.*

interface ChatService {

    // POST /chat
    @POST("chat")
    suspend fun createChat(
        @Body dto: ChatDTOCreate
    ): Response<ChatDTOResponse>


    // PUT /chat/{id}
    @PUT("chat/{id}")
    suspend fun updateChat(
        @Path("id") id: String,
        @Body dto: ChatDTOUpdate
    ): Response<ChatDTOResponse>


    // GET /chat
    @GET("chat")
    suspend fun getAllChats(): Response<List<ChatDTOResponse>>


    // GET /chat/{id}
    @GET("chat/{id}")
    suspend fun getChatById(
        @Path("id") id: String
    ): Response<ChatDTOResponse>


    // GET /chat/{id}/mensagens
    @GET("chat/{id}/mensagens")
    suspend fun getMensagens(
        @Path("id") id: String
    ): Response<List<MensagemDTOResponse>>


    // DELETE /chat/{id}
    @DELETE("chat/{id}")
    suspend fun deleteChat(
        @Path("id") id: String
    ): Response<Unit>  // 204 No Content
}
