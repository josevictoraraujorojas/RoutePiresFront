package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.GeoPoint
import com.example.routepiresfront.data.model.Localizacao
import com.example.routepiresfront.data.model.MototaxistaCreate
import com.example.routepiresfront.data.model.VeiculoCreate
import com.example.routepiresfront.data.repository.MototaxistaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

sealed class CadastroUiState {
    object Idle : CadastroUiState()
    object Loading : CadastroUiState()
    data class Success(val id: String?) : CadastroUiState()
    data class Error(val message: String) : CadastroUiState()
}

// Form state
data class CadastroFormState(
    val nome: String = "",
    val email: String = "",
    val telefone: String = "",
    val senha: String = "",
    val confirmaSenha: String = "",
    val fotoUrl: String = "",
    val cnh: String = "",
    val dataValidade: String = "",
    val servicos: MutableSet<String> = mutableSetOf(),
    val placa: String = "",
    val renavam: String = "",
    val modeloMoto: String = "",
    val anoMoto: String = "",
    val fotoUrlMoto: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun isFirstStepValid() =
        nome.isNotBlank() && email.isNotBlank() && telefone.isNotBlank() &&
                senha.length >= 6 && senha == confirmaSenha

    fun isSecondStepValid() = cnh.isNotBlank() && dataValidade.isNotBlank()

    fun isThirdStepValid() =
        placa.isNotBlank() && renavam.isNotBlank() && modeloMoto.isNotBlank() && anoMoto.toIntOrNull() != null
}

class CadastroMototaxistaViewModel(private val repo: MototaxistaRepository) : ViewModel() {

    private val _form = MutableStateFlow(CadastroFormState())
    val form: StateFlow<CadastroFormState> = _form.asStateFlow()

    private val _uiState = MutableStateFlow<CadastroUiState>(CadastroUiState.Idle)
    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    fun updateForm(update: CadastroFormState.() -> CadastroFormState) {
        _form.value = _form.value.update()
    }

    fun toggleServico(servico: String, selecionado: Boolean) {
        val atual = _form.value.servicos.toMutableSet()
        if (selecionado) atual.add(servico) else atual.remove(servico)
        _form.value = _form.value.copy(servicos = atual)
    }

    fun submitCadastro() {
        val f = _form.value

        if (!f.isFirstStepValid() || !f.isSecondStepValid() || !f.isThirdStepValid()) {
            _uiState.value = CadastroUiState.Error("Preencha todos os campos corretamente.")
            return
        }

        _uiState.value = CadastroUiState.Loading

        val foto = f.fotoUrlMoto.ifBlank { "https://example.com/default.jpg" }

        val veiculo = VeiculoCreate(
            placa = f.placa.trim().uppercase(),
            modelo = f.modeloMoto.trim(),
            renavam = f.renavam.trim(),
            ano = f.anoMoto.toInt(),
            fotoUrl = foto
        )

        // monta Localizacao se tiver coords
        val local = if (f.latitude != null && f.longitude != null) {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val iso = sdf.format(Date())
            Localizacao(localizacao = GeoPoint(f.latitude, f.longitude), timesTamp = iso)
        } else null

        val dto = MototaxistaCreate(
            nome = f.nome.trim(),
            email = f.email.trim(),
            telefone = f.telefone.trim(),
            senha = f.senha,
            fotoUrl = foto,
            cnh = f.cnh.trim(),
            dataValidade = f.dataValidade,
            disponivel = true,
            localizacaoAtual = local,
            veiculo = veiculo,
            servicosOferecidos = f.servicos.toList()
        )

        viewModelScope.launch {
            val res = repo.cadastrar(dto)
            if (res.isSuccess) {
                _uiState.value = CadastroUiState.Success(res.getOrNull()?.id)
            } else {
                val msg = res.exceptionOrNull()?.message ?: "Erro desconhecido"
                _uiState.value = CadastroUiState.Error(msg)
            }
        }
    }
}