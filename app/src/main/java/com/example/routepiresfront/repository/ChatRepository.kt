package br.gov.ifgoiano.routepiresfront.repository

import com.example.routepiresfront.data.model.ChatDTOCreate
import com.example.routepiresfront.data.model.ChatDTOResponse
import com.example.routepiresfront.data.model.ChatDTOUpdate
import com.example.routepiresfront.data.model.MensagemDTOResponse
import br.gov.ifgoiano.routepires.data.remote.ChatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ChatRepository(
    private val api: ChatService
) {

    // Criar chat
    suspend fun createChat(dto: ChatDTOCreate): Response<ChatDTOResponse> =
        withContext(Dispatchers.IO) {
            api.createChat(dto)
        }

    suspend fun getChatsByUsuarioId(idUsuario: String): Response<List<ChatDTOResponse>> =
        withContext(Dispatchers.IO) {
            api.getChatsByUsuarioId(idUsuario)
        }

    // Atualizar chat
    suspend fun updateChat(id: String, dto: ChatDTOUpdate): Response<ChatDTOResponse> =
        withContext(Dispatchers.IO) {
            api.updateChat(id, dto)
        }

    // Buscar todos os chats
    suspend fun getAllChats(): Response<List<ChatDTOResponse>> =
        withContext(Dispatchers.IO) {
            api.getAllChats()
        }

    // Buscar chat por ID
    suspend fun getChatById(id: String): Response<ChatDTOResponse> =
        withContext(Dispatchers.IO) {
            api.getChatById(id)
        }

    // Buscar mensagens de um chat
    suspend fun getMensagensByChatId(id: String): Response<List<MensagemDTOResponse>> =
        withContext(Dispatchers.IO) {
            api.getMensagensByChatId(id)
        }

    // Deletar chat
    suspend fun deleteChat(id: String): Response<Unit> =
        withContext(Dispatchers.IO) {
            api.deleteChat(id)
        }
}
