package com.example.routepiresfront.repository

import com.example.routepiresfront.data.model.ChatDTOCreate
import com.example.routepiresfront.data.model.ChatDTOResponse
import com.example.routepiresfront.data.model.ChatDTOUpdate
import com.example.routepiresfront.data.model.MensagemDTOResponse
import com.example.routepiresfront.data.remote.ApiClient
import com.example.routepiresfront.data.remote.ChatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Repository que usa ApiClient.chatService() por padrão.
 **/
class ChatRepository(){
    private val api: ChatService = ApiClient.chatService()

    suspend fun createChat(dto: ChatDTOCreate): Result<ChatDTOResponse> =
        withContext(Dispatchers.IO) {
            safeCall { api.createChat(dto) }
        }

    suspend fun updateChat(id: String, dto: ChatDTOUpdate): Result<ChatDTOResponse> =
        withContext(Dispatchers.IO) {
            safeCall { api.updateChat(id, dto) }
        }

    suspend fun getAllChats(): Result<List<ChatDTOResponse>> =
        withContext(Dispatchers.IO) {
            safeCall { api.getAllChats() }
        }

    suspend fun getChatById(id: String): Result<ChatDTOResponse> =
        withContext(Dispatchers.IO) {
            safeCall { api.getChatById(id) }
        }

    suspend fun getMensagens(id: String): Result<List<MensagemDTOResponse>> =
        withContext(Dispatchers.IO) {
            safeCall { api.getMensagens(id) }
        }

    suspend fun deleteChat(id: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            safeCall { api.deleteChat(id) }
        }

    private suspend inline fun <T> safeCall(crossinline call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = runCatching { call() }.getOrElse { throw it }

            if (response.isSuccessful) {

                // --- Tratamento correto para 204 No Content ---
                if (response.code() == 204) {
                    @Suppress("UNCHECKED_CAST")
                    return Result.success(Unit as T)
                }

                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Resposta vazia do servidor"))
                }

            } else {
                val msg = "Erro HTTP ${response.code()}: ${response.message()}"
                Result.failure(Exception(msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
