package com.example.routepiresfront.ui.comum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.core.SessionManager
import com.example.routepiresfront.data.model.passageiro.PassageiroCadastroRequest
import com.example.routepiresfront.data.repository.passageiro.PassageiroRepository
import com.example.routepiresfront.databinding.FragmentEditarPerfilBinding
import kotlinx.coroutines.launch
import android.util.Patterns
import android.widget.Toast

class EditarPerfilFragment : Fragment() {
    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!
    private val repository = PassageiroRepository()
    private val passageiroId: String by lazy {
        arguments?.getString("passageiroId")?.trim().takeUnless { it.isNullOrEmpty() }
            ?: SessionManager.obterUsuarioId(requireContext()).orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (passageiroId.isBlank()) {
            toast("Usuário não identificado. Faça login novamente.")
            findNavController().navigateUp()
            return
        }

        carregarDados()

        binding.buttonAtualizarPerfil.setOnClickListener { atualizarPerfil() }

        // Botão cancelar
        binding.buttonCancelarPerfil.setOnClickListener {
            findNavController().navigateUp()
        }

        // Botão voltar (ícone / arrow)
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun carregarDados() {
        toggleLoading(true)
        lifecycleScope.launch {
            when (val resultado = repository.buscarPassageiro(passageiroId)) {
                is Resultado.Sucesso -> {
                    val dados = resultado.dado
                    binding.editNomeCompleto.setText(dados.nome.orEmpty())
                    binding.editEmail.setText(dados.email.orEmpty())
                    binding.editTelefone.setText(dados.telefone.orEmpty())
                }

                is Resultado.Erro -> toast(resultado.mensagem)
                Resultado.Carregando -> {}
            }
            toggleLoading(false)
        }
    }

    private fun atualizarPerfil() {
        val nome = binding.editNomeCompleto.text?.toString()?.trim().orEmpty()
        val email = binding.editEmail.text?.toString()?.trim().orEmpty()
        val telefone = binding.editTelefone.text?.toString()?.trim().orEmpty()
        val senha = binding.editSenha.text?.toString()?.trim().orEmpty()
        val confirmar = binding.editConfirmarSenha.text?.toString()?.trim().orEmpty()

        when {
            nome.isBlank() || email.isBlank() || telefone.isBlank() -> {
                toast("Preencha nome, email e telefone")
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                toast("Informe um email válido")
                return
            }

            telefone.any { !it.isDigit() } || telefone.length !in 10..15 -> {
                toast("Telefone deve ter 10 a 15 dígitos numéricos")
                return
            }

            senha.isNotEmpty() && senha.length < 8 -> {
                toast("Senha deve ter pelo menos 8 caracteres")
                return
            }

            senha.isNotEmpty() && senha != confirmar -> {
                toast("As senhas não coincidem")
                return
            }
        }

        val body = PassageiroCadastroRequest(
            nome = nome,
            email = email,
            telefone = telefone,
            senha = senha.ifBlank { null }
        )

        toggleLoading(true)
        lifecycleScope.launch {
            when (val resultado = repository.atualizarPassageiro(passageiroId, body)) {
                is Resultado.Sucesso -> {
                    toast("Perfil atualizado com sucesso")
                    findNavController().navigateUp()
                }

                is Resultado.Erro -> toast(resultado.mensagem)
                Resultado.Carregando -> {}
            }
            toggleLoading(false)
        }
    }

    private fun toggleLoading(loading: Boolean) {
        binding.buttonAtualizarPerfil.isEnabled = !loading
        binding.buttonCancelarPerfil.isEnabled = !loading
        binding.buttonAtualizarPerfil.text = if (loading) "Salvando..." else "Atualizar"
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}
