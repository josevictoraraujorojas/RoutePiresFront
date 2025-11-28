package br.gov.ifgoiano.routepiresfront.repository

import br.gov.ifgoiano.routepiresfront.data.model.MensagemDTOUpdate
import br.gov.ifgoiano.routepiresfront.data.model.MensagenDTOCreate
import com.example.routepiresfront.data.model.MensagemDTOResponse
import com.example.routepiresfront.data.remote.MensagemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MensagemRepository(
    private val api: MensagemService
) {

    // Criar mensagem
    suspend fun createMensagem(dto: MensagenDTOCreate): Response<MensagemDTOResponse> =
        withContext(Dispatchers.IO) {
            api.createMensagem(dto)
        }

    // Contar mensagens não lidas
    suspend fun contarNaoLidas(chatId: String?, userId: String): Response<Int> =
        withContext(Dispatchers.IO) {
            api.contarNaoLidas(chatId, userId)
        }

    // Atualizar mensagem
    suspend fun updateMensagem(id: String, dto: MensagemDTOUpdate): Response<MensagemDTOResponse> =
        withContext(Dispatchers.IO) {
            api.updateMensagem(id, dto)
        }

    // Listar todas mensagens
    suspend fun getAllMensagens(): Response<List<MensagemDTOResponse>> =
        withContext(Dispatchers.IO) {
            api.getAllMensagens()
        }

    // Buscar mensagem por ID
    suspend fun getMensagemById(id: String): Response<MensagemDTOResponse> =
        withContext(Dispatchers.IO) {
            api.getMensagemById(id)
        }

    // Deletar mensagem
    suspend fun deleteMensagem(id: String): Response<Unit> =
        withContext(Dispatchers.IO) {
            api.deleteMensagem(id)
        }
}
