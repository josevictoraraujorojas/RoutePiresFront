package com.example.routepiresfront.ui.comum

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
import com.example.routepiresfront.data.model.MototaxistaDTOUpdate
import com.example.routepiresfront.viewModel.MototaxistaPerfilViewModel

class EditarPerfilFragment : Fragment() {

    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MototaxistaPerfilViewModel by activityViewModels()
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

        // Carrega perfil para preencher os campos
        userId?.let { viewModel.carregarPerfil(it) }

        // Preenche campos quando o perfil for carregado
        viewModel.perfil.observe(viewLifecycleOwner) { perfil ->
            Log.d("EditarPerfilFragment", "perfil observado: $perfil")
            binding.editNomeCompleto.setText(perfil.nome ?: "")
            binding.editEmail.setText(perfil.email ?: "")
            binding.editTelefone.setText(perfil.telefone ?: "")
        }

        // Observa erros do ViewModel
        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let {
                Log.e("EditarPerfilFragment", "Erro no ViewModel: $it")
                Toast.makeText(requireContext(), "Erro: $it", Toast.LENGTH_LONG).show()
            }
        }

        // Observa status de update para saber quando navegar para trás
        viewModel.updateStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                if (it) {
                    Toast.makeText(requireContext(), "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Falha ao atualizar perfil", Toast.LENGTH_LONG).show()
                }
                // Reseta o status para neutro após tratar
                viewModel.resetUpdateStatus()
            }
        }

        binding.buttonAtualizarPerfil.setOnClickListener {
            Log.d("EditarPerfilFragment", "buttonAtualizarPerfil clicked")
            val nome = binding.editNomeCompleto.text.toString().trim()
            val telefone = binding.editTelefone.text.toString().trim()
            val email = binding.editEmail.text.toString().trim()
            val senha = binding.editSenha.text.toString().trim()

            if (nome.isEmpty()) {
                Toast.makeText(requireContext(), "Informe o nome", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userId == null) {
                Toast.makeText(requireContext(), "Usuário não identificado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val update = MototaxistaDTOUpdate(nome = nome, telefone = telefone, email = if (email.isNotEmpty()) email else null, senha = if (senha.isNotEmpty()) senha else null)
            Log.d("EditarPerfilFragment", "Enviando update: $update")
            // Chama update; a navegação será feita quando updateStatus virar true
            viewModel.atualizarPerfil(userId!!, update)
        }

        binding.buttonCancelarPerfil.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
