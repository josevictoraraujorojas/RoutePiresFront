package com.example.routepiresfront.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.repository.AvaliacaoRepository
import com.example.routepiresfront.data.model.AvaliacaoMototaxistaDTOCreate
import com.example.routepiresfront.data.model.AvaliacaoPassageiroDTOCreate
import kotlinx.coroutines.launch
import java.util.Date

class AvaliacaoViewModel(
    private val avaliacaoRepository: AvaliacaoRepository
) : ViewModel() {

    // 🔥 Usados pelo XML
    val nota = MutableLiveData(0)
    val comentario = MutableLiveData("")

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _erro = MutableLiveData<String?>()
    val erro: LiveData<String?> = _erro

    private val _sucesso = MutableLiveData<Boolean>()
    val sucesso: LiveData<Boolean> = _sucesso

    // 🔥 IDs fixos apenas para testes
    private val usuarioLogadoId = "usuarios/cUKwPBdlmMt95OJuMleA"
    private val corridaId = "2xH8ppkWWORWSiJC3HBn"

    // "PASSAGEIRO" ou "MOTOTAXISTA"
    private val tipoUsuario = "PASSAGEIRO"


    // ===============================================================
    // 🚀 AGORA funciona com o XML → sem parâmetros
    // ===============================================================
    fun enviarAvaliacao() {
        val notaValue = nota.value ?: 0
        val comentarioValue = comentario.value

        if (notaValue == 0) {
            _erro.value = "Selecione uma nota!"
            return
        }

        _loading.value = true
        _erro.value = null
        _sucesso.value = false

        viewModelScope.launch {
            try {
                val result = when (tipoUsuario) {

                    "MOTOTAXISTA" -> {
                        val dto = AvaliacaoPassageiroDTOCreate(
                            nota = notaValue,
                            comentario = comentarioValue,
                            corrida = corridaId
                        )
                        avaliacaoRepository.avaliarPassageiro(dto)
                    }

                    "PASSAGEIRO" -> {
                        val dto = AvaliacaoMototaxistaDTOCreate(
                            nota = notaValue,
                            comentario = comentarioValue,
                            corrida = corridaId,
                        )
                        avaliacaoRepository.avaliarMototaxista(dto)
                    }

                    else -> {
                        _erro.postValue("Tipo de usuário inválido")
                        return@launch
                    }
                }

                if (result.isSuccess) {
                    _sucesso.postValue(true)
                } else {
                    _erro.postValue(result.exceptionOrNull()?.message ?: "Erro ao enviar avaliação")
                }

            } catch (e: Exception) {
                _erro.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
