package com.example.routepiresfront.viewmodel

import androidx.lifecycle.*
import com.example.routepiresfront.repository.DenunciaRepository
import br.gov.ifgoiano.routepiresfront.data.model.DenunciaDTOCreate
import kotlinx.coroutines.launch

class DenunciaViewModel(
    private val repository: DenunciaRepository,
    private val usuarioLogadoId: String,
    private val usuarioDenunciadoId: String
) : ViewModel() {

    // Two-way binding com os dois campos do layout
    val motivo = MutableLiveData<String>("")
    val descricao = MutableLiveData<String>("")

    // Estados da UI
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _sucesso = MutableLiveData<Boolean>()
    val sucesso: LiveData<Boolean> get() = _sucesso

    private val _erro = MutableLiveData<String?>()
    val erro: LiveData<String?> get() = _erro

    /**
     * Envia a denúncia usando os valores atuais de motivo e descrição.
     * Valida campos antes do envio.
     */
    fun enviarDenuncia() {
        val motivoValue = motivo.value?.trim()
        val descricaoValue = descricao.value?.trim()

        // Validação de campos obrigatórios
        if (motivoValue.isNullOrBlank()) {
            _erro.value = "Preencha o motivo da denúncia."
            return
        }

        if (descricaoValue.isNullOrBlank()) {
            _erro.value = "Preencha a descrição da denúncia."
            return
        }

        _loading.value = true

        // Cria DTO para envio
        val dto = DenunciaDTOCreate(
            motivo = motivoValue,
            descricao = descricaoValue,
            denuncianteId = usuarioLogadoId,
            denunciadoId = usuarioDenunciadoId
        )

        // Lança coroutine para chamada assíncrona
        viewModelScope.launch {
            try {
                val response = repository.criarDenuncia(dto)
                if (response.isSuccessful) {
                    _sucesso.value = true
                } else {
                    _erro.value = "Erro ao enviar denúncia: ${response.code()}"
                }
            } catch (e: Exception) {
                _erro.value = "Falha na conexão: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    /** Limpa o erro depois de exibido */
    fun limparErro() {
        _erro.value = null
    }
}
