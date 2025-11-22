package com.example.routepiresfront.ui.passageiro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.routepiresfront.databinding.FragmentEditarPerfilBinding
import com.example.routepiresfront.data.model.PassageiroUpdateDTO
import com.example.routepiresfront.viewModel.PassageiroPerfilViewModel

class EditarPerfilPassageiroFragment : Fragment() {

    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PassageiroPerfilViewModel by activityViewModels()
    private var userId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getString("USER_ID") ?: activity?.intent?.getStringExtra("USER_ID")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId?.let { viewModel.carregarPerfil(it) }

        viewModel.perfil.observe(viewLifecycleOwner) { perfil ->
            Log.d("EditarPerfilPassageiro", "perfil observado: $perfil")
            binding.editNomeCompleto.setText(perfil.nome ?: "")
            binding.editEmail.setText(perfil.email ?: "")
            binding.editTelefone.setText(perfil.telefone ?: "")
        }

        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let {
                Log.e("EditarPerfilPassageiro", "Erro no ViewModel: $it")
                Toast.makeText(requireContext(), "Erro: $it", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.updateStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                if (it) {
                    Toast.makeText(requireContext(), "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Falha ao atualizar perfil", Toast.LENGTH_LONG).show()
                }
                viewModel.resetUpdateStatus()
            }
        }

        binding.buttonAtualizarPerfil.setOnClickListener {
            val nome = binding.editNomeCompleto.text.toString().trim()
            val telefone = binding.editTelefone.text.toString().trim()
            // método de pagamento atualmente não editável nesta tela
            val email = binding.editEmail.text.toString().trim()
            val senha = binding.editSenha.text.toString().trim()
            val confirmarSenha = binding.editConfirmarSenha.text.toString().trim()

            if (nome.isEmpty()) {
                Toast.makeText(requireContext(), "Informe o nome", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se usuário preencheu senha, valida confirmação e força tamanho mínimo
            if (senha.isNotEmpty() || confirmarSenha.isNotEmpty()) {
                if (senha != confirmarSenha) {
                    Toast.makeText(requireContext(), "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (senha.length < 6) {
                    Toast.makeText(requireContext(), "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if (userId == null) {
                Toast.makeText(requireContext(), "Usuário não identificado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val update = PassageiroUpdateDTO(
                nome = nome,
                email = if (email.isNotEmpty()) email else null,
                telefone = telefone,
                senha = if (senha.isNotEmpty()) senha else null
            )
            Log.d("EditarPerfilPassageiro", "Enviando update: $update")
            viewModel.atualizarPerfil(userId!!, update)
        }

        binding.buttonCancelarPerfil.setOnClickListener { findNavController().navigateUp() }
        binding.btnVoltar.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
